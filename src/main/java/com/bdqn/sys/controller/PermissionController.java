package com.bdqn.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.sys.entity.Permission;
import com.bdqn.sys.service.PermissionService;
import com.bdqn.common.utils.DataGridViewResult;
import com.bdqn.common.utils.JSONResult;
import com.bdqn.common.utils.SystemConstant;
import com.bdqn.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-17
 */
@RestController
@RequestMapping("/sys/permission")
public class PermissionController {


    @Resource
    private PermissionService permissionService;

    @RequestMapping("/permissionList")
    public DataGridViewResult permissionList(PermissionVo permissionVo){
        //创建分页对象
        IPage<Permission> page = new Page<Permission>(permissionVo.getPage(),permissionVo.getLimit());
        //创建条件构造器对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        //只查询菜单
        queryWrapper.eq("type", SystemConstant.TYPE_PERMISSION);
        //权限名称查询
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        //权限编号
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPercode()),"percode",permissionVo.getPercode());

        queryWrapper.eq(permissionVo.getId()!=null,"id",permissionVo.getId())
                .or().eq(permissionVo.getId()!=null,"pid", permissionVo.getId());
        //排序
        queryWrapper.orderByAsc("id");
        //调用查询的方法
        permissionService.page(page,queryWrapper);
        //返回数据
        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }

    @RequestMapping("/addPermission")
    public JSONResult addPermission(Permission permission){
        //指定type为Permission菜单类型
        permission.setType(SystemConstant.TYPE_PERMISSION);
        if(permissionService.save(permission)){
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }

    @RequestMapping("/updatePermission")
    public JSONResult updatePermission(Permission permission){
        if(permissionService.updateById(permission)){
            return SystemConstant.UPDATE_SUCCESS;
        }
        return SystemConstant.UPDATE_ERROR;
    }

    @RequestMapping("/deleteById")
    public JSONResult deleteById(int id){
        if(permissionService.removeById(id)){
            return SystemConstant.DELETE_SUCCESS;
        }else{
            return SystemConstant.DELETE_ERROR;
        }
    }

}

