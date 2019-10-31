package com.fh.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.admin.po.Region;

import java.util.List;

public interface IRegionMapper extends BaseMapper<Region> {
    List<Region> queryRegion();

    void addRegion(Region reg);

    void updateRegion(Region reg);

    Long deleteRegion(String[] ids);

    void deleteRegionById(Long id);
}
