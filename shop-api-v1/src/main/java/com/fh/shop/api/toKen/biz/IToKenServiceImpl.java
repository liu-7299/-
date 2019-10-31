package com.fh.shop.api.toKen.biz;

import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("toKenService")
public class IToKenServiceImpl implements IToKenService {

    @Override
    public ServerResponse createToKen() {
        String uuid = UUID.randomUUID().toString();
        RedisUtil.set(uuid,uuid);
        return ServerResponse.success(uuid);
    }

}
