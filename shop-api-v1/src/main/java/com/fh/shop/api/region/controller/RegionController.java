package com.fh.shop.api.region.controller;

import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.region.biz.IRegionService;
import com.fh.shop.api.region.po.Region;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("regions")
@CrossOrigin("*")
public class RegionController {

    @Resource(name="regionService")
    private IRegionService regionService;

    @RequestMapping(method = RequestMethod.GET)
    public ServerResponse queryRegionAll(){
        return regionService.queryRegionAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ServerResponse addRegion(Region region){
        return regionService.addRegion(region);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServerResponse deleteRegion(@PathVariable Integer id){
        return regionService.deleteRegion(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServerResponse queryRegionById(@PathVariable Integer id){
        return regionService.queryRegionById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ServerResponse updateRegion(@RequestBody Region region){
        return regionService.updateRegion(region);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ServerResponse deleteRegions(String ids){
        return regionService.deleteRegions(ids);
    }

    @RequestMapping(value = "/pid/{id}", method = RequestMethod.GET)
    public ServerResponse queryRegionByPid(@PathVariable Integer id){
        return regionService.queryRegionByPid(id);
    }

}
