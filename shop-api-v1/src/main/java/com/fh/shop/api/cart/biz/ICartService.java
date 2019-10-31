package com.fh.shop.api.cart.biz;

import com.fh.shop.api.commons.ServerResponse;

public interface ICartService {
    ServerResponse addCart(Long id, Long commId, Long count);

    ServerResponse queryCart(Long id);

    ServerResponse deleteCart(Long id, String[] commId);
}
