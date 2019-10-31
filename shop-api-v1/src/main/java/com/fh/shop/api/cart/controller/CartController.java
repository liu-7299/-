package com.fh.shop.api.cart.controller;

import com.fh.shop.api.cart.biz.ICartService;
import com.fh.shop.api.commons.Check;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("carts")
public class CartController {

    @Resource(name="cartService")
    private ICartService cartService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("add")
    @Check
    public ServerResponse addCart(Long commId, Long count){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return cartService.addCart(memberVo.getId(),commId,count);
    }

    @GetMapping("query")
    @Check
    public ServerResponse queryCart(){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return cartService.queryCart(memberVo.getId());
     }

    @DeleteMapping("delete")
    @Check
     public ServerResponse deleteCart(String[] commId){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return cartService.deleteCart(memberVo.getId(),commId);
     }

}
