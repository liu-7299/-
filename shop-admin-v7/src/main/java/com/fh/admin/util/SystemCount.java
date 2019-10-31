package com.fh.admin.util;

public class SystemCount {

    public static final String LOGIN_USER = "userInfo:";

    public static final String MENU_LIST = "menuList:";
    public static final String ALL_MENU_LIST = "AllmenuList:";
    public static final String MY_MENU_LIST = "MymenuList:";

    public static final String ERROR_JSP = "/error.jsp";
    public static final String INDEX_LIST_JSP = "/index/index";
    public static final String USER_LIST_JSP = "/user/list";
    public static final String ROLE_LIST_JSP = "/role/list";
    public static final String REGION_LIST_JSP = "/region/list";
    public static final String MENU_LIST_JSP = "/menu/list";
    public static final String COMM_LIST_JSP = "/commodity/list";
    public static final String BRAND_LIST_JSP = "/brand/list";
    public static final String LOG_LIST_JSP = "/log/list";
    public static final String USER_UPDATEPASSWORD_JSP = "/user/updatePassword";
    public static final String MEMBER_LIST_JSP = "/member/list";

    public static final String DEFAULT_LOGIN_PATH = "/skip/skipListJsp.jhtml";

    public static final Integer LOG_STATUS_SUCCESS = 1;
    public static final Integer LOG_STATUS_ERROR = 0;

    public static final Integer LOGIN_ERROR_COUNT = 3;
    public static final String DEFSULT_USER_PASSWORD = "123456";

    public static final String MAIL_HOST = "smtp.qq.com";
    public static final String MAIL_DEFSULT_USER = "729926610@qq.com";
    public static final String MAIL_DEFSULT_PASSWORD = "bpaodpgzwqbvbccd";
    public static final String MAIL_DEFSULT_PERSONAL = "青鱼";

    //验证码存活时间
    public static final int CODE_EXPIRE = 2 * 60;
    //验证码拼接头
    public static final String CODE_HEAD = "code:";
    //域名
    public static final String YU_NAME = "shop.admin.com";
    //读cookie的name
    public static final String COOKIE_READ = "fh_1902";
    //用户信息存活时间
    public static final int USER_EXPIRE = 30 * 60;

}
