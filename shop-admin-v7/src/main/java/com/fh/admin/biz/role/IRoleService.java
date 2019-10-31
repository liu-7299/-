package com.fh.admin.biz.role;

import com.fh.admin.param.RoleParam;
import com.fh.admin.po.Role;
import com.fh.admin.vo.role.RoleVo;

import java.util.List;

public interface IRoleService {
    List<Role> queryRole(RoleParam role);

    void addRole(Role role, String[] nodeIdArr);

    boolean deleteRoleById(Long id);

    RoleVo queryRoleById(Long id);

    void updateRole(Role role, String[] nodeIdArr);

    Long queryRoleCount(RoleParam role);

    List<Role> queryRoleAll();

    List<Long> queryRoleOrMenu(Long id);
}
