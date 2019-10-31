package com.fh.admin.controller;

import com.fh.admin.biz.region.IRegionService;
import com.fh.admin.commons.Log;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.po.Region;
import com.fh.admin.util.SystemCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("region")
public class RegionController {

    @Autowired
    private IRegionService regionService;

    @RequestMapping("skipListJsp")
    public String skipListJsp(){
        return SystemCount.REGION_LIST_JSP;
    }

    @RequestMapping("queryRegion")
    @ResponseBody
    public List queryRegion(){
        List<Map<String,Object>> ids = new ArrayList();
        List<Region> list = regionService.queruRegion();
        for (Region reg : list) {
            Map<String,Object> map = new HashMap();
            map.put("id", reg.getId());
            map.put("pId", reg.getPid());
            map.put("name", reg.getName());
            ids.add(map);
        }
        return ids;
    }

    @RequestMapping("addRegion")
    @ResponseBody
    @Log("添加地区")
    public ServerResponse addRegion(Region reg){
        Long id = regionService.addRegion(reg);
        return ServerResponse.success(id);
    }

    @RequestMapping("updateRegion")
    @ResponseBody
    @Log("修改地区")
    public ServerResponse updateRegion(Region reg){
        regionService.updateRegion(reg);
        return ServerResponse.success();
    }

    @RequestMapping("deleteRegionAll")
    @ResponseBody
    @Log("删除地区")
    public ServerResponse deleteRegionAll(String[] str){
        regionService.deleteRegionAll(str);
        return ServerResponse.success();
    }

    @RequestMapping("queryRegionsById")
    @ResponseBody
    public ServerResponse queryRegionsById(Integer id){
        return regionService.queryRegionsById(id);
    }

}
