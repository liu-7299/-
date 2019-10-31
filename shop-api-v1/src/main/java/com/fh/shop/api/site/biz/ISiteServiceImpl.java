package com.fh.shop.api.site.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.site.mapper.ISiteMapper;
import com.fh.shop.api.site.po.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("siteService")
@Transactional(rollbackFor = Exception.class)
public class ISiteServiceImpl implements ISiteService {

    @Autowired
    private ISiteMapper siteMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse querySiteByMemberId(Long memberId) {
        QueryWrapper<Site> wrapper = new QueryWrapper<>();
        wrapper.eq("memberId",memberId);
        List<Site> sites = siteMapper.selectList(wrapper);
        return ServerResponse.success(sites);
    }

    @Override
    public ServerResponse addSite(Long memberId, Site site) {
        if(site.getStatus() == 1) updateSiteStatus(memberId);
        site.setMemberId(memberId);
        siteMapper.insert(site);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateSite(Long memberId, Site site) {
        if(site.getStatus() == 1) updateSiteStatus(memberId);
        site.setMemberId(memberId);
        siteMapper.updateById(site);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteSiteById(Long id) {
        siteMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse querySiteById(Long id) {
        Site site = siteMapper.selectById(id);
        return ServerResponse.success(site);
    }

    private void updateSiteStatus(Long memberId){
        Site site = new Site();
        site.setStatus(2);
        UpdateWrapper<Site> wrapper = new UpdateWrapper<>();
        wrapper.eq("memberId",memberId);
        siteMapper.update(site, wrapper);
    }

}
