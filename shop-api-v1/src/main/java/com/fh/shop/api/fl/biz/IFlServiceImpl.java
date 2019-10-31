package com.fh.shop.api.fl.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.commons.ServerResponse;
import com.fh.shop.api.fl.mapper.IFlMapper;
import com.fh.shop.api.fl.po.Fl;
import com.fh.shop.api.fl.vo.FlVo;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("flService")
@Transactional(rollbackFor = Exception.class)
public class IFlServiceImpl implements IFlService {

    @Autowired
    private IFlMapper flMapper;


    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryFlById(Integer id) {
        String flListJson = RedisUtil.get("flList");
        List<Fl> fls;
        if(StringUtils.isNotEmpty(flListJson)){
            fls = JSONArray.parseArray(flListJson, Fl.class);
            return ServerResponse.success(toVo(havingFl(id,fls)));
        }
        fls = flMapper.selectList(null);
        flListJson = JSONObject.toJSONString(fls);
        RedisUtil.set("flList",flListJson);
        return ServerResponse.success(toVo(havingFl(id,fls)));
    }

    @Override
    @Transactional(readOnly = true)
    public ServerResponse queryFlAll() {
        String flListJson = RedisUtil.get("flList");
        List<Fl> fls;
        if(StringUtils.isNotEmpty(flListJson)){
            fls = JSONArray.parseArray(flListJson, Fl.class);
            return ServerResponse.success(fls);
        }
        fls = flMapper.selectList(null);
        flListJson = JSONObject.toJSONString(fls);
        RedisUtil.set("flList",flListJson);
        return ServerResponse.success(fls);
    }

    public List<Fl> havingFl(Integer id,List<Fl> fls){
        List<Fl> list = new ArrayList<>();
        for(Fl fl : fls){
            if(fl.getPid() == Long.valueOf(id)){
                list.add(fl);
            }
        }
        return list;
    }

    public List<FlVo> toVo(List<Fl> fls){
        List<FlVo> list = new ArrayList<>();
        for(Fl fl : fls){
            list.add(toVo(fl));
        }
        return list;
    }

    public FlVo toVo(Fl fl){
        FlVo flo = new FlVo();
        flo.setId(fl.getId());
        flo.setName(fl.getName());
        flo.setPid(fl.getPid());
        return flo;
    }

}
