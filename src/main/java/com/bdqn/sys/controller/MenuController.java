package com.bdqn.sys.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.sys.entity.Permission;
import com.bdqn.sys.entity.User;
import com.bdqn.sys.service.PermissionService;
import com.bdqn.sys.service.RoleService;
import com.bdqn.sys.service.UserService;
import com.bdqn.common.utils.*;
import com.bdqn.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Resource
    private PermissionService permissionService;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;


    @RequestMapping("/loadIndexLeftMenu")
    public DataGridViewResult loadIndexLeftMenu(PermissionVo permissionVo, HttpSession session) {
        List<Permission> permissions = new ArrayList<Permission>();
        try {
            QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
            queryWrapper.eq("type", SystemConstant.TYPE_MENU);//只查询菜单
            //获取当前登录用户
            User user = (User) session.getAttribute(SystemConstant.LOGINUSER);
            //如果当前登录用户为超级管理员，则能查看所有菜单
            if (user.getType() == SystemConstant.SUPERUSER) {
                //查询菜单列表
                permissions = permissionService.list(queryWrapper);
            } else {//普通用户：需要根据当前用户的角色及权限加载菜单列表
                //查询菜单列表
                //permissions = permissionService.list(queryWrapper);
                //
                Set<Integer> currentUserRoleIds = userService.findUserRoleByUserId(user.getId());
                Set<Integer> pids = new HashSet<Integer>();
                for (Integer roleId : currentUserRoleIds) {
                    Set<Integer> permissionIds = roleService.findRolePermissionByRoleId(roleId);
                    pids.addAll(permissionIds);
                }
                if (pids.size()>0){
                    queryWrapper.in("id",pids);
                    permissions = permissionService.list(queryWrapper);
                }
            }
            //构建菜单节点集合
            List<TreeNode> treeNodes = new ArrayList<TreeNode>();
            for (Permission permission : permissions) {
                //判断当前节点是否展开，是则为true，否则为false
                Boolean spread = SystemConstant.OPEN_TRUE == permission.getOpen() ? true : false;
                treeNodes.add(new TreeNode(permission.getId(), permission.getPid(),
                        permission.getTitle(), permission.getIcon(),
                        permission.getHref(), spread));
            }
            //构建节点菜单层级关系(参数1：节点集合数据源，参数2：根节点编号)
            List<TreeNode> treeNodeList = TreeNodeBuilder.build(treeNodes, 1);
            //将节点返回出去
            return new DataGridViewResult(treeNodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/loadMenuTreeLeft")
    public DataGridViewResult loadMenuTreeLeft() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        queryWrapper.eq("type", SystemConstant.TYPE_MENU);//只查询菜单
        List<Permission> menuList = permissionService.list(queryWrapper);

        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        for (Permission permission : menuList) {
            //判断当前节点是否展开，是则为true，否则为false
            Boolean spread = SystemConstant.OPEN_TRUE == permission.getOpen() ? true : false;
            treeNodes.add(new TreeNode(permission.getId(), permission.getPid(),
                    permission.getTitle(), permission.getIcon(),
                    permission.getHref(), spread));
        }
        return new DataGridViewResult(treeNodes);
    }


    @RequestMapping("/menuList")
    public DataGridViewResult menulist(PermissionVo permissionVo){
        //创建分页对象
        IPage<Permission> page = new Page<Permission>(permissionVo.getPage(),permissionVo.getLimit());
        //创建条件构造器对象
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        //只查询菜单
        queryWrapper.eq("type",SystemConstant.TYPE_MENU);
        //菜单名称查询
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        //菜单编号
        queryWrapper.eq(permissionVo.getId()!=null,"id",permissionVo.getId())
                .or().eq(permissionVo.getId()!=null,"pid", permissionVo.getId());
        //排序
        queryWrapper.orderByAsc("id");
        //调用查询的方法
        permissionService.page(page,queryWrapper);
        //返回数据
        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }


    @RequestMapping("/addMenu")
    public JSONResult addMenu(Permission permission){
        //指定type为menu菜单类型
        permission.setType(SystemConstant.TYPE_MENU);
        if(permissionService.save(permission)){
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }

    @RequestMapping("/updateMenu")
    public JSONResult updateMenu(Permission permission){
        if(permissionService.updateById(permission)){
            return SystemConstant.UPDATE_SUCCESS;
        }
        return SystemConstant.UPDATE_ERROR;
    }

    @RequestMapping("/checkMenuHasChildren")
    public String checkMenuHasChildren(int id){
        Map<String,Object> map = new HashMap<String,Object>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        //根据父节点查询
        queryWrapper.eq("pid",id);
        //统计菜单数量
        int count = permissionService.count(queryWrapper);
        if(count>0){
            //存在子节点
            map.put(SystemConstant.EXIST,true);
            map.put(SystemConstant.MESSAGE,"对不起，当前菜单下有子节点，无法删除！");
        }else{
            //不存在子节点
            map.put(SystemConstant.EXIST,false);
        }
        //将map集合转换成json
        return JSON.toJSONString(map);
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