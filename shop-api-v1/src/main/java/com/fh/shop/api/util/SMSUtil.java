package com.fh.shop.api.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SMSUtil {

    //发送验证码的请求路径URL
    private static final String URL = "https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String APPKEY = "ff160c47985c2fb2c94e08ce82eb1b79";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APPSECRET = "d1263734530b";
    //验证码长度，范围4～10，默认为4
    private static final String CODELEN = "6";

    public static String sendSMS(String phone){
        //随机数
        String Nonce = UUID.randomUUID().toString();
        //当前时间戳
        String CurTime = System.currentTimeMillis() + "";//String.valueOf((new Date()).getTime() / 1000L);
        //参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
        String CheckSum = CheckSumBuilder.getCheckSum(APPSECRET, Nonce, CurTime);
        //头信息
        Map<String, String> headers = new HashMap<>();
        headers.put("AppKey", APPKEY);
        headers.put("Nonce", Nonce);
        headers.put("CurTime", CurTime);
        headers.put("CheckSum", CheckSum);
        //参数信息
        Map<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("codeLen", CODELEN);
        //调用发送请求方法
        return HttpUtil.send(URL, headers, params);
    }

}
