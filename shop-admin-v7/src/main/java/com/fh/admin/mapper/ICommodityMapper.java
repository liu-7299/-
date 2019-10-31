package com.fh.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.admin.param.CommodityParam;
import com.fh.admin.po.Commodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICommodityMapper extends BaseMapper<Commodity> {


    Long queryCommCount(CommodityParam comm);

    List<Commodity> queryCommodityPage(CommodityParam comm);

    List<Commodity> queryCommodityPam(CommodityParam commodityParam);
}
