package com.fh.admin.biz.region;

import com.fh.admin.commons.ServerResponse;
import com.fh.admin.po.Region;

import java.util.List;

public interface IRegionService {
    List<Region> queruRegion();

    Long addRegion(Region reg);

    void updateRegion(Region reg);

    void deleteRegionAll(String[] str);

    ServerResponse queryRegionsById(Integer id);
}
