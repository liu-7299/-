package com.fh.shop.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.commons.Check;
import com.fh.shop.api.commons.ResponseEnum;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemCount;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class
Interceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //跨域请求，*代表允许全部类型
        response.setHeader("Access-Control-Allow-Origin", "*");
        //允许请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        //用来指定本次预检请求的有效期，单位为秒，在此期间不用发出另一条预检请求
        response.setHeader("Access-Control-Max-Age", "3600");
        //请求包含的字段内容，如有多个可用哪个逗号分隔如下
        response.setHeader("Access-Control-Allow-Headers", "x-auth,content-type,toKen");
        //访问控制允许凭据，true为允许
        /*response.setHeader("Access-Control-Allow-Credentials", "true");*/
        String method1 = request.getMethod();
        if("OPTIONS".equalsIgnoreCase(method1)){
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //没有加check的方法不需要登录
        if(!method.isAnnotationPresent(Check.class)){
            return true;
        }
        String header = request.getHeader("x-auth");
        //头信息丢失
        if(null == header){
            throw new GlobalException(ResponseEnum.TOP_LOSE);
        }
        //头信息缺失
        String[] split = header.split("\\.");
        if(split.length != 2){
            throw new GlobalException(ResponseEnum.TOP_LACK);
        }
        //头信息被篡改
        String encode = split[0];
        String secret = split[1];
        String str = Md5Util.sign(encode, SystemCount.LOGIN_SECRET);
        String s = Base64.getEncoder().encodeToString(str.getBytes());
        if(!s.equals(secret)){
            throw new GlobalException(ResponseEnum.TOP_ERROR);
        }
        //登录超时
        encode = new String(Base64.getDecoder().decode(encode));
        MemberVo memberVo = JSONObject.parseObject(encode, MemberVo.class);
        String userName = memberVo.getUserName();
        String uuid = memberVo.getUuid();
        Boolean exists = RedisUtil.exists(KeyUtil.getUserKey(userName, uuid));
        if(!exists){
            throw new GlobalException(ResponseEnum.LOGIN_TIMEOUT);
        }
        RedisUtil.expire(KeyUtil.getUserKey(userName, uuid), SystemCount.EXIET_TIME);
        //放入request作用域
        request.setAttribute("memberVo",memberVo);
        return true;
    }

}
