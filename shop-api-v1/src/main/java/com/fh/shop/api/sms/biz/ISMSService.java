package com.fh.shop.api.sms.biz;

import com.fh.shop.api.commons.ServerResponse;

public interface ISMSService {
    ServerResponse phoneCode(String phone);
}
