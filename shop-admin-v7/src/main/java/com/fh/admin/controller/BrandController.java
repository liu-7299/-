package com.fh.admin.controller;

import com.fh.admin.biz.brand.IBrandService;
import com.fh.admin.commons.DataTableMap;
import com.fh.admin.commons.Log;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.param.BrandParam;
import com.fh.admin.po.Brand;
import com.fh.admin.util.SystemCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @RequestMapping("skipListJsp")
    public String skipListJsp(){
        return SystemCount.BRAND_LIST_JSP;
    }

    @RequestMapping("queryBrandPage")
    @ResponseBody
    public DataTableMap queryBrandPage(BrandParam brand){
        Long count = brandService.queryBrandCount(brand);
        List<Brand> list = brandService.queryBrandPage(brand);
        DataTableMap map = new DataTableMap(brand.getDraw(),count,count,list);
        return map;
    }

    @RequestMapping("addBrand")
    @ResponseBody
    @Log("添加品牌")
    public ServerResponse addBrand(Brand brand){
        return brandService.addBrand(brand);
    }

    @RequestMapping("deleteBrand")
    @ResponseBody
    @Log("删除品牌")
    public ServerResponse deleteBrand(Long id){
        return brandService.deleteBrand(id);
    }

    @RequestMapping("queryBrandById")
    @ResponseBody
    public ServerResponse queryBrandById(Long id){
        return brandService.queryBrandById(id);
    }

    @RequestMapping("updateBrand")
    @ResponseBody
    @Log("修改品牌")
    public ServerResponse updateBrand(Brand brand,String oldImgPath){
        return brandService.updateBrand(brand,oldImgPath);
    }

    @RequestMapping("queryBrandAll")
    @ResponseBody
    public ServerResponse queryBrandAll(){
        return brandService.queryBrandAll();
    }

    @RequestMapping("updateBrandSort")
    @ResponseBody
    @Log("修改sort")
    public ServerResponse updateBrandSort(Long id,Integer sort){
        return brandService.updateBrandSort(id,sort);
    }

    @RequestMapping("updateBrandSell")
    @ResponseBody
    @Log("修改热销状态")
    public ServerResponse updateBrandSell(Long id,Integer sell){
        return brandService.updateBrandSell(id,sell);
    }

}
