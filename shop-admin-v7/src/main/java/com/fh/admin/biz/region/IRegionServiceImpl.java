package com.fh.admin.biz.region;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.mapper.IRegionMapper;
import com.fh.admin.po.Region;
import com.fh.admin.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IRegionServiceImpl implements IRegionService {

    @Autowired
    private IRegionMapper regionMapper;

    @Override
    public List<Region> queruRegion() {
        return regionMapper.queryRegion();
    }

    @Override
    public Long addRegion(Region reg) {
        regionMapper.addRegion(reg);
        return reg.getId();
    }

    @Override
    public void updateRegion(Region reg) {
        regionMapper.updateRegion(reg);
    }

    @Override
    public void deleteRegionAll(String[] ids) {
        regionMapper.deleteRegion(ids);
    }

    @Override
    public ServerResponse queryRegionsById(Integer id) {
        String regionListJson = RedisUtil.get("regionList");
        List<Region> regions;
        if(StringUtils.isNotEmpty(regionListJson)){
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
            if(Long.valueOf(id).equals(region.getPid())){
                regions.add(region);
            }
        }
        return regions;
    }

}
