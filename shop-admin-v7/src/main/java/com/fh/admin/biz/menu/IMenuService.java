package com.fh.admin.biz.menu;

import com.fh.admin.po.Menu;

import java.util.List;
import java.util.Map;

public interface IMenuService {
    List<Map<String,Object>> queryMenu();

    Long addMenu(Menu menu);

    void updateMenu(Menu menu);

    boolean deleteMenuAll(String[] str);

    List<Menu> queryMenuByUserId(Long id);

    List<Menu> queryMenuOrUser(Long id);

    List<Menu> queryMenuUser();
}
