package com.fh.admin.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void writeCookie(String name, String value, String domain, HttpServletResponse response){
        Cookie cookie = new Cookie(name,value);
        cookie.setDomain(domain);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String readCookie(String name,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(null == cookies) return "";
        for (Cookie cookie : cookies) {
            if(name.equals(cookie.getName())) return cookie.getValue();
        }
        return "";
    }

}
