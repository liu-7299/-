package com.fh.admin.mapper;

import com.fh.admin.po.Menu;

import java.util.List;

public interface IMenuMapper {
    List<Menu> queryMenu();

    void addMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenuAll(String[] ids);

    Integer queryMentCountByDel(String[] ids);

    List<Menu> queryMenuByUserId(Long id);

    List<Menu> queryMenuOrUser(Long id);

    List<Menu> queryMenuUser();
}
