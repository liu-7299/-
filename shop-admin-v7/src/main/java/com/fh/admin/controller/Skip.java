package com.fh.admin.controller;

import com.fh.admin.po.Menu;
import com.fh.admin.util.SystemCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("skip")
public class Skip {

    @Autowired
    HttpServletRequest request;

    @RequestMapping("skipListJsp")
    public String skipListJsp(){
        return SystemCount.INDEX_LIST_JSP;
    }

    @RequestMapping("skip")
    public String getUrl(String url){//, HttpServletRequest request
        //request.getSession().setAttribute("url",url);
        List<Menu> list = (List<Menu>)request.getSession().getAttribute(SystemCount.MENU_LIST);
        List<String> strArr = new ArrayList();
        for(Menu menu : list){
            int i = menu.getUrl().lastIndexOf("=");
            if(i != -1) strArr.add(menu.getUrl().substring(i+1));
            else strArr.add(menu.getUrl());
        }
        if(strArr.indexOf(url) == -1){
            //url = "/index/index";
        }
        return url;//forward
    }

}
