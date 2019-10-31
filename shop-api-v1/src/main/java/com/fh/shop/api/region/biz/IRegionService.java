package com.fh.shop.api.region.biz;

import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.region.po.Region;

public interface IRegionService {
    ServerResponse queryRegionAll();

    ServerResponse addRegion(Region region);

    ServerResponse deleteRegion(Integer id);

    ServerResponse queryRegionById(Integer id);

    ServerResponse updateRegion(Region region);

    ServerResponse deleteRegions(String ids);

    ServerResponse queryRegionByPid(Integer id);
}
