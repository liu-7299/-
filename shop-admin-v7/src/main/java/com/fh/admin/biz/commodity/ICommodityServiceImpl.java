package com.fh.admin.biz.commodity;

import com.fh.admin.commons.ServerResponse;
import com.fh.admin.mapper.IBrandMapper;
import com.fh.admin.mapper.ICommodityMapper;
import com.fh.admin.param.CommodityParam;
import com.fh.admin.po.Brand;
import com.fh.admin.po.Commodity;
import com.fh.admin.util.DateUtil;
import com.fh.admin.vo.commodity.CommodityVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ICommodityServiceImpl implements ICommodityService {

    @Autowired
    private ICommodityMapper commodityMapper;

    @Override
    public Long queryCommCount(CommodityParam comm) {
        return commodityMapper.queryCommCount(comm);
    }

    @Override
    public List<Commodity> queryCommodityPam(CommodityParam commodityParam) {
        List<Commodity> list = commodityMapper.queryCommodityPam(commodityParam);
        return list;
    }

    @Override
    public List<CommodityVo> queryCommodityPage(CommodityParam comm) {
        List<Commodity> list = commodityMapper.queryCommodityPage(comm);
        List<CommodityVo> listt = new ArrayList();
        for(Commodity com : list ){
            CommodityVo cmm = dataVoToPo(com);
            listt.add(cmm);
        }
        return listt;
    }

    @Override
    public ServerResponse addCommodity(Commodity comm) {
        commodityMapper.insert(comm);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteCommodity(Long id) {
        commodityMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse queryCommodityById(Long id) {
        Commodity comm = commodityMapper.selectById(id);
        CommodityVo com = dataVoToPo(comm);
        return ServerResponse.success(com);
    }

    @Override
    public ServerResponse updateCommodity(Commodity comm) {
        commodityMapper.updateById(comm);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateCommodityStatus(Long id) {
        Commodity comm = commodityMapper.selectById(id);
        Integer status = comm.getStatus();
            if(comm.getStatus() != null && status == 1){
                status = 2;
            }else{
                status = 1;
            }
        comm.setStatus(status);
        commodityMapper.updateById(comm);
        return ServerResponse.success();
    }

    @Override
    public List<CommodityVo> queryCommodity() {
        List<Commodity> list = commodityMapper.selectList(null);
        List<CommodityVo> lit = new ArrayList();
        for(Commodity comm : list){
            CommodityVo com = dataVoToPo(comm);
            lit.add(com);
        }
        return lit;
    }

    public CommodityVo dataVoToPo(Commodity comm){
        CommodityVo commv = new CommodityVo();
        commv.setId(comm.getId());
        commv.setName(comm.getName());
        commv.setPrice(String.valueOf(comm.getPrice()));
        commv.setImgPath(comm.getImgPath());
        commv.setCreateTime(DateUtil.dateToString(comm.getCreateTime(),DateUtil.STRING_H_M_S));
        commv.setStock(comm.getStock());
        commv.setStatus(comm.getStatus());
        commv.setBrandId(comm.getBrandId());
        commv.setFlName(comm.getFlName());
        commv.setBrandName(comm.getBrandName());
        return commv;
    }

}
