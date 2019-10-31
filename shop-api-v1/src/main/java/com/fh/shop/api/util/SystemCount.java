package com.fh.shop.api.util;

public class SystemCount {

    //手机验证码前缀
    public static final String PHONE_CODE = "phoneCode:";
    //登录秘钥
    public static final String LOGIN_SECRET = "qwdc6@hh#5sd%";
    //存活时间
    public static final  int EXIET_TIME = 60 * 30;

    //订单状态
    public static final Integer ORDER_A = 10;//待支付
    public static final Integer ORDER_B = 20;//代发货
    public static final Integer ORDER_C = 30;//待收货

    //发送邮件
    public static final String MAIL_HOST = "smtp.qq.com";
    public static final String MAIL_DEFSULT_USER = "729926610@qq.com";
    public static final String MAIL_DEFSULT_PASSWORD = "bpaodpgzwqbvbccd";
    public static final String MAIL_DEFSULT_PERSONAL = "青鱼";
}
