package com.fh.shop.api.member.controller;

import com.fh.shop.api.commons.Check;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("members")
@CrossOrigin("*")
public class MemberController {

    @Resource(name="memberService")
    private IMemberService memberService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("login")
    public ServerResponse login(Member member){
        return memberService.login(member);
    }

    @GetMapping("loginOut")
    @Check
    public ServerResponse loginOut(){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        RedisUtil.del(KeyUtil.getUserKey(memberVo.getUserName(),memberVo.getUuid()));
        return ServerResponse.success();
    }

    @GetMapping("gainMessage")
    @Check
    public ServerResponse gainMessage(){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return ServerResponse.success(memberVo);
    }

    @PostMapping
    public ServerResponse addMember(Member member){
        return memberService.addMember(member);
    }

    @GetMapping(value = "userName")
    public ServerResponse queryMemberByUserName(String userName){
        return memberService.queryMemberByUserName(userName);
    }

    @GetMapping(value = "email")
    public ServerResponse queryMemberByEmail(String email){
        return memberService.queryMemberByEmail(email);
    }

    @GetMapping(value = "phone")
    public ServerResponse queryMemberByPhone(String phone){
        return memberService.queryMemberByPhone(phone);
    }

}
