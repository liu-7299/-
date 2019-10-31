package com.fh.admin.controller;

import com.fh.admin.biz.member.IMemberService;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.param.MemberParam;
import com.fh.admin.util.SystemCount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("member")
public class MemberController {

    @Resource(name="memberService")
    private IMemberService memberService;

    @RequestMapping("skipListJsp")
    public String skipListJsp(){
        return SystemCount.MEMBER_LIST_JSP;
    }

    @RequestMapping("queryMemberByPage")
    @ResponseBody
    public ServerResponse queryMemberByPage(MemberParam memberParam){
        return memberService.queryMemberByPage(memberParam);
    }

}
