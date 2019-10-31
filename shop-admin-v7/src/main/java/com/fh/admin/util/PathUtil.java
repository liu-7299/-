package com.fh.admin.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class PathUtil {

    public static String getRequestPath(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getLockUserContent(String name,String path){
        String str = "<h1>尊敬的"+name+"，您的账号在"+DateUtil.dateToString(new Date(),DateUtil.STRING_H_M_S)+"尝试登陆，密码连续错误三次，用户已锁定，登陆地点为"+path+",如不是您本人操作，请尽快更新密码</h1>";
        return str;
    }

    public static String getUpdateUserPasswordByEmailContent(String realName,String newPassword){
        String str = "<h1>尊敬的"+realName+"，您的新密码为"+newPassword+"</h1>";
        return str;
    }

}
