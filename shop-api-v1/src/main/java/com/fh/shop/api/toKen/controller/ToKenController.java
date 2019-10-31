package com.fh.shop.api.toKen.controller;

import com.fh.shop.api.commons.Check;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.toKen.biz.IToKenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("toKen")
public class ToKenController {

    @Resource(name="toKenService")
    private IToKenService toKenService;

    @GetMapping
    @Check
    public ServerResponse createToKen(){
        return toKenService.createToKen();
    }

}
