package com.fh.shop.api.commodity.controller;

import com.fh.shop.api.commodity.biz.ICommodityService;
import com.fh.shop.api.commodity.vo.CommodityVo;
import com.fh.shop.api.commons.Check;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("commodity")
@CrossOrigin("*")
public class CommodityController {

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping()
    public ServerResponse queryCommodity(){
        return commodityService.queryCommodity();
    }

    @GetMapping("queryShopCar")
    @Check
    public ServerResponse queryShopCar(){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return commodityService.queryShopCar(memberVo.getId());
    }

    @PostMapping("addOrUpdateShopCar")
    @Check
    public ServerResponse addOrUpdateShopCar(String key, String count){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return commodityService.addOrUpdateShopCar(memberVo.getId(),key,count);
    }
    @DeleteMapping("daleteShopCar")
    @Check
    public ServerResponse daleteShopCar(String[] key){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return commodityService.daleteShopCar(memberVo.getId(),key);
    }
    @DeleteMapping("daletesShopCar")
    @Check
    public ServerResponse daletesShopCar(){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return commodityService.daletesShopCar(memberVo.getId());
    }

}
