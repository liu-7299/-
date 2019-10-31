package com.fh.admin.controller;

import com.fh.admin.biz.fl.IFlService;
import com.fh.admin.commons.Log;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.po.Fl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("fl")
public class FlController {

    @Autowired
    private IFlService flService;

    @RequestMapping("skipListJsp")
    public String skipListJsp(){
        return "/fl/list";
    }

    @RequestMapping("queryFl")
    @ResponseBody
    public List queryFl(){
        List<Map<String,Object>> ids = new ArrayList();
        List<Fl> list = flService.queruFl();
        for (Fl fl : list) {
            Map<String,Object> map = new HashMap();
            map.put("id", fl.getId());
            map.put("pId", fl.getPid());
            map.put("name", fl.getName());
            ids.add(map);
        }
        return ids;
    }

    @RequestMapping("addFl")
    @ResponseBody
    @Log("添加")
    public ServerResponse addFl(Fl fl){
        Integer id = flService.addFl(fl);
        return ServerResponse.success(id);
    }

    @RequestMapping("updateFl")
    @ResponseBody
    @Log("修改")
    public ServerResponse updateFl(Fl fl){
        flService.updateFl(fl);
        return ServerResponse.success();
    }

    @RequestMapping("deleteFlAll")
    @ResponseBody
    @Log("删除")
    public ServerResponse deleteFlAll(String[] str){
        flService.deleteFlAll(str);
        return ServerResponse.success();
    }

    @RequestMapping("queryFlsById")
    @ResponseBody
    public ServerResponse queryFlsById(Integer id){
        List<Fl> list = flService.queryFlsById(id);
        return ServerResponse.success(list);
    }

}
