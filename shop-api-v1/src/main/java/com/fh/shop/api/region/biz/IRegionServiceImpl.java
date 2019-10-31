package com.fh.shop.api.region.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.commons.ResponseEnum;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.region.mapper.IRegionMapper;
import com.fh.shop.api.region.po.Region;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("regionService")
@Transactional(rollbackFor = Exception.class)
public class IRegionServiceImpl implements IRegionService {

    @Autowired
    private IRegionMapper regionMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryRegionAll() {
        QueryWrapper<Region> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Region> regions = regionMapper.selectList(wrapper);
        return ServerResponse.success(regions);
    }

    @Override
    public ServerResponse addRegion(Region region) {
        if(null == region) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        regionMapper.insert(region);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteRegion(Integer id) {
        if(null == id) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        regionMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryRegionById(Integer id) {
        if(null == id) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        Region region = regionMapper.selectById(id);
        return ServerResponse.success(region);
    }

    @Override
    public ServerResponse updateRegion(Region region) {
        if(null == region) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        regionMapper.updateById(region);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteRegions(String ids) {
        if(null == ids || "".equals(ids)) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        String[] idArr = ids.split(",");
        List<Integer> list = new ArrayList<>();
        for (String s : idArr) {
            list.add(Integer.valueOf(s));
        }
        regionMapper.deleteBatchIds(list);
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryRegionByPid(Integer id) {
        String regionListJson = RedisUtil.get("regionList");
        List<Region> regions;
        if(StringUtils.isNotEmpty(regionListJson)){
            System.out.println("不用查");
            regions = JSONArray.parseArray(regionListJson, Region.class);
            return ServerResponse.success(hacingRegionByPid(id,regions));
        }
        QueryWrapper<Region> wrapper = new QueryWrapper<>();
        regions = regionMapper.selectList(wrapper);
        regionListJson = JSONObject.toJSONString(regions);
        RedisUtil.set("regionList",regionListJson);
        return ServerResponse.success(hacingRegionByPid(id,regions));
    }

    private List<Region> hacingRegionByPid(Integer id,List<Region> list){
        List<Region> regions = new ArrayList<>();
        for(Region region : list){
            if((region.getPid()).equals(id)){
                regions.add(region);
            }
        }
        return regions;
    }

}
