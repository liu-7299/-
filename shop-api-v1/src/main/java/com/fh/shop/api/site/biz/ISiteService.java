package com.fh.shop.api.site.biz;

import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.site.po.Site;

public interface ISiteService {
    ServerResponse querySiteByMemberId(Long memberId);

    ServerResponse addSite(Long memberId, Site site);

    ServerResponse updateSite(Long memberId, Site site);

    ServerResponse deleteSiteById(Long id);

    ServerResponse querySiteById(Long id);
}
