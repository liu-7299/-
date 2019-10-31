package com.fh.admin.biz.menu;

import com.fh.admin.mapper.IMenuMapper;
import com.fh.admin.po.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("serviceMenu")
public class IMenuServiceImpl implements IMenuService {

    @Autowired
    private IMenuMapper menuMapper;

    @Override
    public List<Map<String,Object>> queryMenu() {
        List<Menu> list = menuMapper.queryMenu();
        List<Map<String,Object>> menuList = new ArrayList();
        for(Menu menu : list){
            Map map = new HashMap();
            map.put("id",menu.getId());
            map.put("name",menu.getName());
            map.put("pId",menu.getPid());
            map.put("type",menu.getType());
            map.put("url",menu.getUrl());
            menuList.add(map);
        }
        return menuList;
    }

    @Override
    public Long addMenu(Menu menu) {
        menuMapper.addMenu(menu);
        return menu.getId();
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateMenu(menu);
    }

    @Override
    public boolean deleteMenuAll(String[] ids) {
        Integer i = menuMapper.queryMentCountByDel(ids);
        if(i == 0){
            menuMapper.deleteMenuAll(ids);
            return true;
        }
        return false;
    }

    @Override
    public List<Menu> queryMenuByUserId(Long id) {
        return menuMapper.queryMenuByUserId(id);
    }

    public List<Menu> queryMenuOrUser(Long id) {
        return menuMapper.queryMenuOrUser(id);
    }

    @Override
    public List<Menu> queryMenuUser() {
        return menuMapper.queryMenuUser();
    }

    /*public void getToList(List<Menu> list){

        if(list.size() > 0){
            List<Long> longMenu = new ArrayList();
            for(Menu menu : list){
                longMenu.add(menu.getId());
            }
            List<Menu> ltt = menuMapper.queryMenuOrUser(longMenu);
            for(Menu menu : ltt){
                llt.add(menu);
            }
            getToList(ltt);
        }
    }*/

}
