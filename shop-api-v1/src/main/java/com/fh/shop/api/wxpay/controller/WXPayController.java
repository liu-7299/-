package com.fh.shop.api.wxpay.controller;

import com.fh.shop.api.commons.Check;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.wxpay.biz.IWXPayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("wxPay")
public class WXPayController {

    @Resource(name = "wxPayService")
    private IWXPayService wxPayService;

    @GetMapping("create")
    @Check
    public ServerResponse createCodeUrl(MemberVo memberVo){
        return wxPayService.createCodeUrl(memberVo.getId());
    }

    @GetMapping("query")
    @Check
    public ServerResponse queryCodeUrl(MemberVo memberVo){
        return wxPayService.queryCodeUrl(memberVo.getId());
    }

}
