package com.fh.shop.api.order.biz;

import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.order.param.OrderParam;

public interface IOrderService {
    ServerResponse addOrder(Long id, OrderParam orderParam);
}
