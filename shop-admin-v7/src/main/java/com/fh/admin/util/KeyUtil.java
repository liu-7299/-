package com.fh.admin.util;

public class KeyUtil {

    public static String buildCodeKey(String key){
        return SystemCount.CODE_HEAD + key;
    }

    public static String buildUserKey(String key){
        return SystemCount.LOGIN_USER + key;
    }

    public static String buildMenuKey(String key){
        return SystemCount.MENU_LIST + key;
    }

    public static String buildMyMenuKey(String key){
        return SystemCount.MY_MENU_LIST + key;
    }

}
