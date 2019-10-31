package com.fh.shop.api.commodity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.commodity.po.Commodity;
import org.apache.ibatis.annotations.Param;

public interface ICommodityMapper extends BaseMapper<Commodity> {

    Long updateCommStatus(@Param("count") Long count, @Param("id") Long id);

}
