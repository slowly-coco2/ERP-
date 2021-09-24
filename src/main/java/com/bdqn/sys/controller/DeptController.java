package com.bdqn.sys.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.sys.entity.Dept;
import com.bdqn.sys.service.DeptService;
import com.bdqn.common.utils.DataGridViewResult;
import com.bdqn.common.utils.JSONResult;
import com.bdqn.common.utils.SystemConstant;
import com.bdqn.common.utils.TreeNode;
import com.bdqn.sys.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-18
 */
@RestController
@RequestMapping("/sys/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    @RequestMapping("/loadDeptTreeLeft")
    public DataGridViewResult loadDeptTreeLeft(){
        List<Dept> deptList = deptService.list();
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();

        for (Dept dept : deptList) {
            Boolean spread = dept.getOpen()== SystemConstant.OPEN_TRUE ? true : false;
            TreeNode treeNode = new TreeNode();
            treeNode.setId(dept.getId());
            treeNode.setPid(dept.getPid());
            treeNode.setTitle(dept.getTitle());
            treeNode.setSpread(spread);
            treeNodes.add(treeNode);
        }
        return  new DataGridViewResult(treeNodes);
    }

    @RequestMapping("/deptList")
    public  DataGridViewResult deptList(DeptVo deptVo){
        IPage<Dept> page = new Page<Dept>(deptVo.getPage(),deptVo.getLimit());
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<Dept>();
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()),"address",deptVo.getAddress());
        queryWrapper.eq(deptVo.getId() != null, "id",deptVo.getId())
                .or().eq(deptVo.getId()!=null,"pid",deptVo.getId());
        queryWrapper.orderByAsc("id");
        deptService.page(page,queryWrapper);
        return  new DataGridViewResult(page.getTotal(),page.getRecords());
    }


    @RequestMapping("/addDept")
    public JSONResult addDept(Dept dept){
        dept.setCreatetime(new Date());
        if (deptService.save(dept)) {
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }


    @RequestMapping("/updateDept")
    public JSONResult updateDept(Dept dept){
        dept.setCreatetime(new Date());
        if (deptService.updateById(dept)) {
            return SystemConstant.UPDATE_SUCCESS;
        }
        return SystemConstant.UPDATE_ERROR;
    }

    @RequestMapping("/checkDeptHasChildren")
    public String checkDeptHasChildren(int id){
        Map<String,Object> map = new HashMap<String,Object>();
        QueryWrapper<Dept> deptQueryWrapper = new QueryWrapper<Dept>();
        deptQueryWrapper.eq("pid",id);
        int count = deptService.count(deptQueryWrapper);
        if (count>0){
            map.put(SystemConstant.EXIST,true);
            map.put(SystemConstant.MESSAGE,"对不起，当前部门下有子节点，无法删除");
        }else{
            map.put(SystemConstant.EXIST,false);
        }

        return JSON.toJSONString(map);
    }


    @RequestMapping("/deleteById")
    public  JSONResult deleteById(int id){
        if (deptService.removeById(id)){
            return SystemConstant.DELETE_SUCCESS;
        }else{
            return SystemConstant.DELETE_ERROR;
        }
    }
}

