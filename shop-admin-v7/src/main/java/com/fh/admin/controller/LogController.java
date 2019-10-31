package com.fh.admin.controller;

import com.fh.admin.biz.log.ILogService;
import com.fh.admin.commons.DataTableMap;
import com.fh.admin.param.LogParam;
import com.fh.admin.util.SystemCount;
import com.fh.admin.vo.log.LogVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("log")
public class LogController {

    @Resource(name="logService")
    private ILogService logService;

    @RequestMapping("skipListJsp")
    public String skipListJsp(){
        return SystemCount.LOG_LIST_JSP;
    }

    @RequestMapping("questLogByPage")
    @ResponseBody
    public DataTableMap questLogByPage(LogParam log){
        Long count = logService.queryLogCount(log);
        List<LogVo> list = logService.queryLogByPage(log);
        DataTableMap data = new DataTableMap(log.getDraw(),count,count,list);
        return data;
    }

}
