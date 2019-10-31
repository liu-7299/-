package com.fh.shop.api.commodity.biz;

import com.fh.shop.api.commons.ServerResponse;

public interface ICommodityService {
    ServerResponse queryCommodity();

    ServerResponse addOrUpdateShopCar(Long id, String key, String count);

    ServerResponse daleteShopCar(Long id, String... key);

    ServerResponse daletesShopCar(Long id);

    ServerResponse queryShopCar(Long id);
}
