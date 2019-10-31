package com.fh.admin.biz.fl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.admin.mapper.IFlMapper;
import com.fh.admin.po.Fl;
import com.fh.admin.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IFlServiceImpl implements IFlService {

    @Autowired
    private IFlMapper flMapper;

    @Override
    public List<Fl> queruFl() {
        String flListJson = RedisUtil.get("flList");
        List<Fl> fls;
        if(StringUtils.isNotEmpty(flListJson)){
            fls = JSONArray.parseArray(flListJson, Fl.class);
            return fls;
        }
        fls = flMapper.selectList(null);
        flListJson = JSONObject.toJSONString(fls);
        RedisUtil.set("flList",flListJson);
        return fls;
    }

    @Override
    public Integer addFl(Fl fl) {
        RedisUtil.del("flList");
        flMapper.addFl(fl);
        return fl.getId();
    }

    @Override
    public void updateFl(Fl fl) {
        flMapper.updateFl(fl);
    }

    @Override
    public void deleteFlAll(String[] str) {
        flMapper.deleteFl(str);
    }

    @Override
    public List<Fl> queryFlsById(Integer id) {
        String flListJson = RedisUtil.get("flList");
        List<Fl> fls;
        if(StringUtils.isNotEmpty(flListJson)){
            fls = JSONArray.parseArray(flListJson, Fl.class);
            return havingFl(id, fls);
        }
        fls = flMapper.selectList(null);
        flListJson = JSONObject.toJSONString(fls);
        RedisUtil.set("flList",flListJson);
        return havingFl(id,fls);
    }

    public List<Fl> havingFl(Integer id,List<Fl> fls){
        List<Fl> list = new ArrayList<>();
        for(Fl fl : fls){
            if(fl.getPid().equals(Long.valueOf(id))){
                list.add(fl);
            }
        }
        return list;
    }

}
