package com.fh.shop.api.interceptor;


import com.fh.shop.api.commons.Idem;
import com.fh.shop.api.commons.ResponseEnum;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.util.RedisUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class IdemInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //没有加这个注解的不需要进行下面的判断
        if(!method.isAnnotationPresent(Idem.class)){
            return true;
        }
        String header = request.getHeader("toKen");
        //头信息丢失
        if(null == header){
            throw new GlobalException(ResponseEnum.TOP_LOSE);
        }
        Boolean exists = RedisUtil.exists(header);
        if(!exists){
            //redis中没有 请求超时
            throw new GlobalException(ResponseEnum.REQUEST_TIMEOUT);
        }
        Long del = RedisUtil.del(header);
        if(del <= 0){
            //不相等
            throw new GlobalException(ResponseEnum.REQUEST_TIMEOUT);
        }
        return true;
    }

}
