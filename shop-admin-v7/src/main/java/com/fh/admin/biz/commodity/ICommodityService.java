package com.fh.admin.biz.commodity;

import com.fh.admin.commons.ServerResponse;
import com.fh.admin.param.CommodityParam;
import com.fh.admin.po.Commodity;
import com.fh.admin.vo.commodity.CommodityVo;

import java.util.List;
import java.util.Map;

public interface ICommodityService {
    Long queryCommCount(CommodityParam comm);

    List<CommodityVo> queryCommodityPage(CommodityParam comm);

    ServerResponse addCommodity(Commodity comm);

    ServerResponse deleteCommodity(Long id);

    ServerResponse queryCommodityById(Long id);

    ServerResponse updateCommodity(Commodity comm);

    ServerResponse updateCommodityStatus(Long id);

    List<CommodityVo> queryCommodity();

    List<Commodity> queryCommodityPam(CommodityParam commodityParam);
}
