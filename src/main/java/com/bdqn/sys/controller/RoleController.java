package com.bdqn.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.sys.entity.Permission;
import com.bdqn.sys.entity.Role;
import com.bdqn.sys.service.PermissionService;
import com.bdqn.sys.service.RoleService;
import com.bdqn.common.utils.DataGridViewResult;
import com.bdqn.common.utils.JSONResult;
import com.bdqn.common.utils.SystemConstant;
import com.bdqn.common.utils.TreeNode;
import com.bdqn.sys.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-21
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @RequestMapping("/roleList")
    public DataGridViewResult roleList(RoleVo roleVo){
        IPage<Role> page = new Page<Role>(roleVo.getPage(),roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRolecode()),"rolecode",roleVo.getRolecode());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRolename()),"rolename",roleVo.getRolename());
        queryWrapper.orderByAsc("id");
        roleService.page(page,queryWrapper);
        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }

    @RequestMapping("/addRole")
    public JSONResult addRole(Role role){
        role.setCreatetime(new Date());//添加时间
        if (roleService.save(role)){
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }

    @RequestMapping("/updateRole")
    public JSONResult updateRole(Role role){
        if (roleService.updateById(role)){
            return SystemConstant.UPDATE_SUCCESS;
        }
        return SystemConstant.UPDATE_ERROR;
    }

    @RequestMapping("/deleteById")
    public JSONResult deleteById(Role role){
        if (roleService.removeById(role)){
            return SystemConstant.DELETE_SUCCESS;
        }
        return SystemConstant.DELETE_ERROR;
    }

    //初始化权限菜单树
    @RequestMapping("/initPermissionByRoleId")
    public DataGridViewResult initPermissionByRoleId(int roleId){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        List<Permission> permissions = permissionService.list(queryWrapper);
        List<Permission> currentPermissions = new ArrayList<Permission>();
        List<Integer> currentPermissionIds = permissionService.findRolePermissionByRoleId(roleId);
        if (currentPermissionIds!=null && currentPermissionIds.size()>0){
            queryWrapper.in("id",currentPermissionIds);
            currentPermissions = permissionService.list(queryWrapper);
        }

        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        for (Permission p1 : permissions) {
            String checkArr = "0";
            for (Permission p2 : currentPermissions) {
                if (p1.getId() == p2.getId()){
                    checkArr="1";
                    break;
                }
            }
            Boolean spread = (p1.getOpen()==null || p1.getOpen()==1) ? true : false;
            treeNodes.add(new TreeNode(p1.getId(),p1.getPid(),p1.getTitle(),spread,checkArr));
        }
        return new DataGridViewResult(treeNodes);
    }

    @RequestMapping("/saveRolePermission")
    public JSONResult saveRolePermission(int rid,String ids){
        try {
            if (roleService.saveRolePermission(rid,ids)){
                return SystemConstant.DISTRIBUTE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  SystemConstant.DISTRIBUTE_ERROR;

    }

}

