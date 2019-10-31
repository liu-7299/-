package com.fh.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.admin.commons.Page;
import com.fh.admin.po.Brand;

import java.util.List;

public interface IBrandMapper extends BaseMapper<Brand> {
    Long queryBrandCount(Page page);

    List<Brand> queryBrandPage(Page page);
}
