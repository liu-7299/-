package com.fh.shop.api.order.controller;

import com.fh.shop.api.commons.Check;
import com.fh.shop.api.commons.Idem;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.order.biz.IOrderService;
import com.fh.shop.api.order.param.OrderParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Resource(name="orderService")
    private IOrderService orderService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("addOrder")
    @Check
    @Idem
    public ServerResponse addOrder(OrderParam orderParam){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return orderService.addOrder(memberVo.getId(),orderParam);
    }

}
