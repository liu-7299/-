package com.fh.admin.biz.brand;

import com.fh.admin.commons.ServerResponse;
import com.fh.admin.param.BrandParam;
import com.fh.admin.po.Brand;

import java.util.List;

public interface IBrandService {
    List<Brand> queryBrandPage(BrandParam brand);

    Long queryBrandCount(BrandParam brand);

    ServerResponse addBrand(Brand brand);

    ServerResponse deleteBrand(Long id);

    ServerResponse queryBrandById(Long id);

    ServerResponse updateBrand(Brand brand,String oldImgPath);

    ServerResponse queryBrandAll();

    ServerResponse updateBrandSort(Long id, Integer sort);

    ServerResponse updateBrandSell(Long id, Integer sell);
}
