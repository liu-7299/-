package com.fh.shop.api.site.controller;

import com.fh.shop.api.commons.Check;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.site.biz.ISiteService;
import com.fh.shop.api.site.po.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("sites")
public class SiteController {

    @Resource(name="siteService")
    private ISiteService siteService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("querySiteByMemberId")
    @Check
    public ServerResponse querySiteByMemberId(){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return siteService.querySiteByMemberId(memberVo.getId());
    }

    @GetMapping("querySiteById")
    @Check
    public ServerResponse querySiteById(Long id){
        return siteService.querySiteById(id);
    }

    @PostMapping("addSite")
    @Check
    public ServerResponse addSite(Site site){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return siteService.addSite(memberVo.getId(), site);
    }

    @PutMapping("updateSite")
    @Check
    public ServerResponse updateSite(@RequestBody Site site){
        MemberVo memberVo = (MemberVo) request.getAttribute("memberVo");
        return siteService.updateSite(memberVo.getId(), site);
    }

    @DeleteMapping("deleteSiteById")
    @Check
    public ServerResponse deleteSiteById(Long id){
        return siteService.deleteSiteById(id);
    }

}
