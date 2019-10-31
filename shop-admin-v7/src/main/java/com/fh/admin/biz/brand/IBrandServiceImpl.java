package com.fh.admin.biz.brand;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.mapper.IBrandMapper;
import com.fh.admin.param.BrandParam;
import com.fh.admin.po.Brand;
import com.fh.admin.util.OSSUtil;
import com.fh.admin.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IBrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public List<Brand> queryBrandPage(BrandParam brand) {
        return brandMapper.queryBrandPage(brand);
    }

    @Override
    public Long queryBrandCount(BrandParam brand) {
        return brandMapper.queryBrandCount(brand);
    }

    @Override
    public ServerResponse addBrand(Brand brand) {
        //添加时清除redis缓存服务器指定数据
        RedisUtil.del("*");
        brandMapper.insert(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBrand(Long id) {
        brandMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse queryBrandById(Long id) {
        Brand brand = brandMapper.selectById(id);
        return ServerResponse.success(brand);
    }

    @Override
    public ServerResponse updateBrand(Brand brand,String oldImgPath) {
        if(!(brand.getImgPath()).equals(oldImgPath) && StringUtils.isNotEmpty(oldImgPath)){
            OSSUtil.deleteOSSFile(oldImgPath);
        }
        brandMapper.updateById(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse queryBrandAll() {
        String brandListJson = RedisUtil.get("*");
        if(StringUtils.isEmpty(brandListJson)){
            //redis缓存服务器中没有 从数据库查找
            List<Brand> brands = brandMapper.selectList(null);
            //将数据转化为json格式字符串 放入redis缓存服务器
            brandListJson = JSONObject.toJSONString(brands);
            RedisUtil.set("*",brandListJson);
            //将数据返回给前台
            return ServerResponse.success(brands);
        }else{
            //redis缓存服务器中有 直接获取json格式字符串转换为java对象
            List<Brand> brands = JSONArray.parseArray(brandListJson, Brand.class);
            return ServerResponse.success(brands);
        }
    }

    @Override
    public ServerResponse updateBrandSort(Long id, Integer sort) {
        Brand brand = new Brand();
        brand.setId(id);
        brand.setSort(sort);
        brandMapper.updateById(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateBrandSell(Long id, Integer sell) {
        Brand brand = new Brand();
        brand.setId(id);
        brand.setSell(sell);
        brandMapper.updateById(brand);
        return ServerResponse.success();
    }
}
