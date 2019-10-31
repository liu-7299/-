package com.fh.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.fh.admin.biz.menu.IMenuService;
import com.fh.admin.commons.Log;
import com.fh.admin.commons.ResponseEnum;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.po.Menu;
import com.fh.admin.po.User;
import com.fh.admin.servlet.DistributedSession;
import com.fh.admin.util.KeyUtil;
import com.fh.admin.util.RedisUtil;
import com.fh.admin.util.SystemCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Resource(name="serviceMenu")
    private IMenuService menuService;

    @RequestMapping("skipListJsp")
    public String skipListJsp(){
        return SystemCount.MENU_LIST_JSP;
    }

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping("queryMenu")
    @ResponseBody
    public ServerResponse queryMenu(){
        List<Map<String,Object>> list = menuService.queryMenu();
        return ServerResponse.success(list);
    }
    @RequestMapping("addMenu")
    @ResponseBody
    @Log("添加菜单")
    public ServerResponse addMenu(Menu menu){
        Long id = menuService.addMenu(menu);
        return ServerResponse.success(id);
    }
    @RequestMapping("updateMenu")
    @ResponseBody
    @Log("修改菜单")
    public ServerResponse updateMenu(Menu menu){
        menuService.updateMenu(menu);
        return ServerResponse.success();
    }
    @RequestMapping("deleteMenuAll")
    @ResponseBody
    @Log("删除菜单")
    public ServerResponse deleteMenuAll(String[] str){
        boolean boo = menuService.deleteMenuAll(str);
        if(boo){
            return ServerResponse.success();
        }
        return ServerResponse.error(ResponseEnum.DELETE_ERROR);
    }

    @RequestMapping("queryMenuByUserId")
    @ResponseBody
    public ServerResponse queryMenuByUserId(){
        //List<Menu> list = (List<Menu>)request.getSession().getAttribute(SystemCount.MENU_LIST);
        String sessionId = DistributedSession.getSessionId(request, response);
        String lists = RedisUtil.get(KeyUtil.buildMenuKey(sessionId));
        List<Menu> list = JSONArray.parseArray(lists, Menu.class);
        return ServerResponse.success(list);
    }

}
