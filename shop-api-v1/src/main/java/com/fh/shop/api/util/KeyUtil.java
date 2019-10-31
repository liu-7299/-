package com.fh.shop.api.util;

public class KeyUtil {

    //返回手机验证码组合前缀
    public static String getPhoneKey(String phone){
        return SystemCount.PHONE_CODE + phone;
    }
    //redis用户名的key
    public static String getUserKey(String userName, String uuid){
        return userName + ":" + uuid;
    }
    //购物车id
    public static String getShopCarId(String id){
        return "shopCar:" + id;
    }

    //会员在购物车中的field
    public static String getCartMemberId(String id){
        return "member:" + id;
    }

    //会员在redis中的key value存放支付记录日志
    public static String getPayLogId(String id){
        return "payLog:" + id;
    }

}
