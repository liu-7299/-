package com.fh.shop.api.sms.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.commons.ResponseEnum;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service("smsService")
public class ISMSServiceImpl implements ISMSService {

    @Override
    public ServerResponse phoneCode(String phone) {
        //手机号为空
        if(StringUtils.isEmpty(phone)) return ServerResponse.error(ResponseEnum.PHONE_IS_NULL);
        //定义正则
        String pattern = "^1[3578]\\d{9}";//或者"^1(3|5|7|8)\\d{9}"
        //不符合手机号规则
        if(!Pattern.matches(pattern,phone)) return ServerResponse.error(ResponseEnum.PHONE_IS_ERROR);
        String s = SMSUtil.sendSMS(phone);
        JSONObject code = JSONObject.parseObject(s);
        //发送错误
        if(!code.get("code").equals(200)) return ServerResponse.error(ResponseEnum.SEND_ERROR);
        RedisUtil.setex(KeyUtil.getPhoneKey(phone),String.valueOf(code.get("obj")),60 * 2);
        return ServerResponse.success(ResponseEnum.SEND_SUCCESS);//发送成功
    }

}
