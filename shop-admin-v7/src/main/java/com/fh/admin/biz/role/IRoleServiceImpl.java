package com.fh.admin.biz.role;

import com.fh.admin.mapper.IRoleMapper;
import com.fh.admin.param.RoleParam;
import com.fh.admin.po.Role;
import com.fh.admin.po.RoleOrMenu;
import com.fh.admin.vo.role.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IRoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleMapper roleMapper;

    @Override
    public List<Role> queryRole(RoleParam role) {
        return roleMapper.queryRole(role);
    }

    @Override
    public Long queryRoleCount(RoleParam role) {
        return roleMapper.queryRoleCount(role);
    }

    @Override
    public List<Role> queryRoleAll() {
        return roleMapper.queryRoleAll();
    }

    @Override
    public List<Long> queryRoleOrMenu(Long id) {
        return roleMapper.queryRoleOrMenu(id);
    }

    @Override
    public void addRole(Role role,String[] nodeIdArr) {
        roleMapper.addRole(role);
        addRoleOrMenu(role,nodeIdArr);
    }

    @Override
    public boolean deleteRoleById(Long id) {
        Integer count = roleMapper.queryRoleCountDel(id);
        if(count == 0){
            roleMapper.deleteRoleById(id);
            deleteRoleOrMenu(id);
            return true;
        }
        return false;
    }

    @Override
    public RoleVo queryRoleById(Long id) {
        Role role = roleMapper.queryRoleById(id);
        return getRoleToRoleVo(role);
    }

    @Override
    public void updateRole(Role role,String[] nodeIdArr) {
        roleMapper.updateRole(role);
        deleteRoleOrMenu(role.getId());
        addRoleOrMenu(role,nodeIdArr);
    }

    public void addRoleOrMenu(Role role,String[] nodeIdArr){
        if(null != nodeIdArr && nodeIdArr.length > 0){
            List<RoleOrMenu> list = new ArrayList();
            for (int i=0;i<nodeIdArr.length;i++){
                RoleOrMenu rom = new RoleOrMenu();
                rom.setRid(role.getId());
                rom.setMid(Long.valueOf(nodeIdArr[i]));
                list.add(rom);
            }
            roleMapper.addRoleOrMenu(list);
        }
    }

    public void deleteRoleOrMenu(Long roleId){
        roleMapper.deleteRoleOrMenu(roleId);
    }

    public RoleVo getRoleToRoleVo(Role role){
        RoleVo rol = new RoleVo();
        rol.setId(role.getId());
        rol.setName(role.getName());
        return rol;
    }

}
