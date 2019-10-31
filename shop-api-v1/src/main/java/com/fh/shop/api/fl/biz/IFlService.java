package com.fh.shop.api.fl.biz;

import com.fh.shop.api.commons.ServerResponse;

public interface IFlService {
    ServerResponse queryFlById(Integer id);

    ServerResponse queryFlAll();
}
