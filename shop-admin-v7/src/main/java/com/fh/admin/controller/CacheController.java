package com.fh.admin.controller;

import com.fh.admin.commons.ServerResponse;
import com.fh.admin.util.RedisUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cache")
public class CacheController {

    @RequestMapping("closeHotBrandCache")
    public ServerResponse closeHotBrandCache(){
        RedisUtil.del("hotBrandList");
        return ServerResponse.success();
    }

    @RequestMapping("closeShowCommodity")
    public ServerResponse closeShowCommodity(){
        RedisUtil.del("showCommodityList");
        return ServerResponse.success();
    }

}
