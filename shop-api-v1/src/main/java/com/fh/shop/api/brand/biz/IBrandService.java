package com.fh.shop.api.brand.biz;

import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.brand.param.BrandParam;

public interface IBrandService {
    ServerResponse queryBrandBySell();

    ServerResponse queryBrandByPage(BrandParam brandParam);

    ServerResponse addBrand(Brand brand);

    ServerResponse deleteBrand(Long id);

    ServerResponse queryBrandById(Long id);

    ServerResponse updateBrand(Brand brand);

    ServerResponse deleteBrands(Long[] ids);
}
