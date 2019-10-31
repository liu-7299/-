package com.fh.shop.api.brand.controller;

import com.fh.shop.api.brand.biz.IBrandService;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.commons.Check;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.brand.param.BrandParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("brand")
@CrossOrigin("*")
public class BrandController {

    @Resource(name = "brandService")
    private IBrandService brandService;

    @RequestMapping(method = RequestMethod.GET)
    @Check
    public ServerResponse queryBrandBySell(){
        return brandService.queryBrandBySell();
    }

    @RequestMapping(value = "page", method = RequestMethod.GET)//value = "page",
    public ServerResponse queryBrandByPage(BrandParam brandParam){
        return brandService.queryBrandByPage(brandParam);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ServerResponse addBrand(Brand brand){
        return brandService.addBrand(brand);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServerResponse deleteBrand(@PathVariable Long id){
        return brandService.deleteBrand(id);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ServerResponse deleteBrands(Long[] ids){
        return brandService.deleteBrands(ids);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServerResponse queryBrandById(@PathVariable Long id){
        return brandService.queryBrandById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ServerResponse updateBrand(@RequestBody Map<String, String> map){
        Brand brand = new Brand();
        brand.setId(Long.valueOf(map.get("id")));
        brand.setName(String.valueOf(map.get("name")));
        brand.setImgPath(String.valueOf(map.get("imgPath")));
        brand.setSell(Integer.valueOf(map.get("sell")));
        brand.setSort(Integer.valueOf(map.get("sort")));
        return brandService.updateBrand(brand);
    }

}
