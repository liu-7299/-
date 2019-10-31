package com.fh.shop.api.fl.controller;

import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.fl.biz.IFlService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("fl")
@CrossOrigin("*")
public class FlController {

    @Resource(name="flService")
    private IFlService  flService;

    @GetMapping("/{id}")
    public ServerResponse queryFlById(@PathVariable Integer id){
        return flService.queryFlById(id);
    }

    @GetMapping()
    public ServerResponse queryFlAll(){
        return flService.queryFlAll();
    }

}
