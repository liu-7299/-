package com.fh.shop.api.commodity.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.commodity.mapper.ICommodityMapper;
import com.fh.shop.api.commodity.po.Commodity;
import com.fh.shop.api.commodity.vo.CommodityVo;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.util.DateUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class ICommodityServiceImpl implements ICommodityService {

    @Resource
    private ICommodityMapper commodityMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryCommodity() {
        String showCommodityListJson = RedisUtil.get("showCommodityList");
        if(StringUtils.isNotEmpty(showCommodityListJson)){
            List<CommodityVo> commodityVos = JSONArray.parseArray(showCommodityListJson, CommodityVo.class);
            return ServerResponse.success(commodityVos);
        }
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.apply("1 = 1");
        wrapper.eq("status",1);
        List<Commodity> commodities = commodityMapper.selectList(wrapper);
        List<CommodityVo> list = toListVo(commodities);
        showCommodityListJson = JSONObject.toJSONString(list);
        RedisUtil.setex("showCommodityList",showCommodityListJson,30);
        return ServerResponse.success(list);
    }

    private List<CommodityVo> toListVo(List<Commodity> commodities) {
        List<CommodityVo> list = new ArrayList<>();
        for(Commodity comm : commodities){
            CommodityVo commvo = new CommodityVo();
            commvo.setId(comm.getId());
            commvo.setName(comm.getName());
            commvo.setImgPath(comm.getImgPath());
            commvo.setPrice(comm.getPrice().toString());
            commvo.setCreateTime(DateUtil.dateToString(comm.getCreateTime(),DateUtil.STRING_H_M_S));
            commvo.setStatus(comm.getStatus());
            commvo.setStock(comm.getStock());
            list.add(commvo);
        }
        return list;
    }

    @Override
    public ServerResponse addOrUpdateShopCar(Long id, String key, String count) {
        String commId = KeyUtil.getShopCarId(String.valueOf(id));
        String hget = RedisUtil.hget(commId, key);
        if(null == hget){
            RedisUtil.hset(commId,key,count);
        }else{
            Integer i = Integer.valueOf(hget) + Integer.valueOf(count);
            RedisUtil.hset(commId,key,String.valueOf(i));
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse daleteShopCar(Long id, String... key) {
        RedisUtil.hdel(KeyUtil.getShopCarId(String.valueOf(id)),key);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse daletesShopCar(Long id) {
        RedisUtil.del(KeyUtil.getShopCarId(String.valueOf(id)));
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryShopCar(Long id) {
        Map<String, String> map = RedisUtil.hgetall(KeyUtil.getShopCarId(String.valueOf(id)));
        List<Long> list = new ArrayList<>();
        for (String s : map.keySet()) {
            list.add(Long.valueOf(map.get(s)));
        }
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.in("id",list);
        List<Commodity> commodities = commodityMapper.selectList(wrapper);
        List<CommodityVo> vos = toListVo(commodities);
        for (CommodityVo vo : vos) {
            for (String s : map.keySet()) {
                if(vo.getId().equals(Long.valueOf(s))){
                    vo.setCount(map.get(s));
                    break;
                }
            }
        }
        return ServerResponse.success(vos);
    }

}
