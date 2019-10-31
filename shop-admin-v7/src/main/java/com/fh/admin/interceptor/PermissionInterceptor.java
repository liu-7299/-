package com.fh.admin.interceptor;

import com.alibaba.fastjson.JSONArray;
import com.fh.admin.po.Menu;
import com.fh.admin.servlet.DistributedSession;
import com.fh.admin.util.KeyUtil;
import com.fh.admin.util.RedisUtil;
import com.fh.admin.util.SystemCount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //Map<String,String[]> pramMap = request.getParameterMap();
        String requestPath = request.getServletPath();//请求路径
        String path = request.getServerName();//电脑ip
        ServletContext servletContext = request.getServletContext();
        String sessionId = DistributedSession.getSessionId(request, response);
        RedisUtil.expire(KeyUtil.buildUserKey(sessionId),SystemCount.USER_EXPIRE);
        RedisUtil.expire(KeyUtil.buildMenuKey(sessionId),SystemCount.USER_EXPIRE);
        RedisUtil.expire(KeyUtil.buildMyMenuKey(sessionId),SystemCount.USER_EXPIRE);
        String llts = RedisUtil.get(KeyUtil.buildMyMenuKey(sessionId));
        List<Menu> llt = JSONArray.parseArray(llts,Menu.class);
        String lts = RedisUtil.get(SystemCount.ALL_MENU_LIST);
        List<Menu> lt = JSONArray.parseArray(lts,Menu.class);
        if(llt.size()>0 && lt.size() > 0){
            for(Menu menu : llt){
                if(StringUtils.isNotEmpty(menu.getUrl()) && requestPath.contains(menu.getUrl())){
                    return true;
                }
            }
            for(Menu menu : lt){
                if(StringUtils.isNotEmpty(menu.getUrl()) && requestPath.contains(menu.getUrl())){
                    //如果是ajax请求响应头会有x-requested-with
                    if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
                        //response.setContentType("text/html;charset=UTF-8");
                        //response.setHeader("Cache-Control", "no-cache");
                            //response.setCharacterEncoding("UTF-8");可以解决中文乱码 但ajax请求接受类型为json才能接受到json类型值
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        //response.setContentType("text/html;charset=utf-8");
                        writer.write("{\"code\":\"9999\",\"msg\":\"权限不足，请联系管理员\"}");
                        return false;
                    }else{
                        //非ajax请求时，session失效的处理
                        response.sendRedirect(SystemCount.ERROR_JSP);
                        return false;
                    }
                }
            }
        }
        return true;
        //return super.preHandle(request, response, handler);
    }
}
