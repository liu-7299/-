package com.fh.admin.servlet;

import com.fh.admin.util.SystemCount;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class DistributedSession {

    public static String getSessionId(HttpServletRequest request, HttpServletResponse response){
        String sessionId = CookieUtil.readCookie(SystemCount.COOKIE_READ, request);
        if(StringUtils.isEmpty(sessionId)){
            sessionId = UUID.randomUUID().toString();
            CookieUtil.writeCookie(SystemCount.COOKIE_READ,sessionId,SystemCount.YU_NAME,response);
        }
        return sessionId;
    }

}
