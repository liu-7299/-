package com.fh.admin.controller;

import com.fh.admin.biz.role.IRoleService;
import com.fh.admin.commons.DataTableMap;
import com.fh.admin.commons.Log;
import com.fh.admin.commons.ResponseEnum;
import com.fh.admin.commons.ServerResponse;
import com.fh.admin.param.RoleParam;
import com.fh.admin.po.Role;
import com.fh.admin.util.SystemCount;
import com.fh.admin.vo.role.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("skipListJsp")
    public String skipListJsp(){
        return SystemCount.ROLE_LIST_JSP;
    }

    @RequestMapping("queryRoleAll")
    @ResponseBody
    public ServerResponse queryRoleAll(){
        List<Role> list = roleService.queryRoleAll();
        return ServerResponse.success(list);
    }

    @RequestMapping("queryRole")
    @ResponseBody
    public DataTableMap queryRole(RoleParam role){
        Long count = roleService.queryRoleCount(role);
        List<Role> list = roleService.queryRole(role);
        DataTableMap map = new DataTableMap(role.getDraw(),count,count,list);
        return map;
    }

    @RequestMapping("addRole")
    @ResponseBody
    @Log("添加角色")
    public ServerResponse addRole(Role role,String[] nodeIdArr){
        roleService.addRole(role,nodeIdArr);
        return ServerResponse.success();
    }

    @RequestMapping("deleteRoleById")
    @ResponseBody
    @Log("删除角色")
    public ServerResponse deleteRoleById(Long id){
        boolean boo = roleService.deleteRoleById(id);
        if(boo){
            return ServerResponse.success();
        }
        return ServerResponse.error(ResponseEnum.DELETE_ERROR);
    }

    @RequestMapping("queryRoleById")
    @ResponseBody
    public ServerResponse queryRoleById(Long id){
        RoleVo role = roleService.queryRoleById(id);
        role.setList(roleService.queryRoleOrMenu(id));
        return ServerResponse.success(role);
    }

    @RequestMapping("updateRole")
    @ResponseBody
    @Log("修改角色")
    public ServerResponse updateRole(Role role,String[] nodeIdArr){
        roleService.updateRole(role,nodeIdArr);
        return ServerResponse.success();
    }


}
