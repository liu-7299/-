package com.fh.shop.api.brand.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.shop.api.brand.mapper.IBrandMapper;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.brand.vo.BrandVo;
import com.fh.shop.api.commons.DataTableMap;
import com.fh.shop.api.commons.ResponseEnum;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.brand.param.BrandParam;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("brandService")
@Transactional(rollbackFor = Exception.class)
public class IBrandServiceImpl implements IBrandService {

    @Resource
    private IBrandMapper brandMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryBrandBySell() {
        String hotBrandListJson = RedisUtil.get("hotBrandList");
        if(StringUtils.isNotEmpty(hotBrandListJson)){
            List<BrandVo> brandVos = JSONArray.parseArray(hotBrandListJson, BrandVo.class);
            return ServerResponse.success(brandVos);
        }
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        wrapper.apply("1 = 1");
        wrapper.eq("sell",1);
        wrapper.orderByAsc("sort");
        List<Brand> brands = brandMapper.selectList(wrapper);
        hotBrandListJson = JSONObject.toJSONString(brands);
        RedisUtil.setex("hotBrandList",hotBrandListJson,30);
        return ServerResponse.success(brands);
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryBrandByPage(BrandParam brandParam) {
        Integer i = (brandParam.getStart()/brandParam.getLength())+1;
        //IPage<T> page = new Page<>(当前页,每页条数);
        Page<Brand> page = new Page<>(i,brandParam.getLength());
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(brandParam.getName()),"name",brandParam.getName());
        IPage<Brand> brandIPage = brandMapper.selectPage(page, wrapper);
        List<Brand> records = brandIPage.getRecords();
        long total = brandIPage.getTotal();
        DataTableMap data = new DataTableMap(brandParam.getDraw(),total,total,records);
        return ServerResponse.success(data);
    }

    @Override
    public ServerResponse addBrand(Brand brand) {
        if(null == brand) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        brandMapper.insert(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBrand(Long id) {
        if(null == id) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        brandMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryBrandById(Long id) {
        if(null == id) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        Brand brand = brandMapper.selectById(id);
        if(null == brand) return ServerResponse.error(ResponseEnum.RESULT_IS__NULL);
        return ServerResponse.success(brand);
    }

    @Override
    public ServerResponse updateBrand(Brand brand) {
        if(null == brand) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        brandMapper.updateById(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBrands(Long[] ids) {
        if(null == ids) return ServerResponse.error(ResponseEnum.PARAM_IS_NULL);
        List<Long> lt = new ArrayList<>();
        for (Long id : ids) {
            lt.add(id);
        }
        brandMapper.deleteBatchIds(lt);
        return ServerResponse.success();
    }
}
