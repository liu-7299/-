package com.fh.admin.mapper;

import com.fh.admin.param.RoleParam;
import com.fh.admin.po.Role;
import com.fh.admin.po.RoleOrMenu;

import java.util.List;

public interface IRoleMapper {
    List<Role> queryRole(RoleParam role);

    void addRole(Role role);

    void deleteRoleById(Long id);

    Role queryRoleById(Long id);

    void updateRole(Role role);

    Long queryRoleCount(RoleParam role);

    List<Role> queryRoleAll();

    void addRoleOrMenu(List<RoleOrMenu> list);

    void deleteRoleOrMenu(Long roleId);

    List<Long> queryRoleOrMenu(Long id);

    Integer queryRoleCountDel(Long id);
}
