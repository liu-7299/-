package com.fh.shop.api.wxpay.biz;

import com.fh.shop.api.commons.ServerResponse;

public interface IWXPayService {
    ServerResponse createCodeUrl(Long id);

    ServerResponse queryCodeUrl(Long id);
}
