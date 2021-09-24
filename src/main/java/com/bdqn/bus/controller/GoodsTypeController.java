package com.bdqn.bus.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.bus.entity.GoodsType;
import com.bdqn.bus.service.GoodsTypeService;
import com.bdqn.bus.vo.GoodsTypeVo;
import com.bdqn.common.utils.DataGridViewResult;
import com.bdqn.common.utils.JSONResult;
import com.bdqn.common.utils.SystemConstant;
import com.bdqn.common.utils.TreeNode;
import com.bdqn.sys.entity.Dept;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-23
 */
@RestController
@RequestMapping("/bus/goodsType")
public class GoodsTypeController {

    @Resource
    private GoodsTypeService goodsTypeService;


    @RequestMapping("/loadGoodsTypeLeft")
    public DataGridViewResult loadGoodsTypeLeft(){
        List<GoodsType> goodsTypeList = goodsTypeService.list();
        List<TreeNode> treeNodes = new ArrayList<>();

        for (GoodsType goodsType : goodsTypeList) {
            Boolean spread = goodsType.getOpen()== SystemConstant.OPEN_TRUE ? true : false;
            TreeNode treeNode = new TreeNode();
            treeNode.setId(goodsType.getId());
            treeNode.setPid(goodsType.getPid());
            treeNode.setTitle(goodsType.getTitle());
            treeNode.setSpread(spread);
            treeNodes.add(treeNode);
        }
        return  new DataGridViewResult(treeNodes);
    }


    @RequestMapping("/goodsTypeList")
    public DataGridViewResult goodsTypeList(GoodsTypeVo goodsTypeVo){
        IPage<GoodsType> page = new Page<GoodsType>(goodsTypeVo.getPage(),goodsTypeVo.getLimit());
        List<GoodsType> goodsTypeList = new ArrayList<>();
        QueryWrapper<GoodsType> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(goodsTypeVo.getTitle()),"title",goodsTypeVo.getTitle());
        queryWrapper.eq(goodsTypeVo.getId() != null, "id",goodsTypeVo.getId())
                .or().eq(goodsTypeVo.getId()!=null,"pid",goodsTypeVo.getId());
        queryWrapper.orderByAsc("id");
        goodsTypeService.page(page,queryWrapper);
        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }

    @RequestMapping("/addGoodsType")
    public JSONResult addGoodsType(GoodsType goodsType){
        if (goodsTypeService.save(goodsType)){
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }


    @RequestMapping("/updateGoodsType")
    public JSONResult updateGoodsType(GoodsType goodsType){
        if (goodsTypeService.updateById(goodsType)){
            return SystemConstant.UPDATE_SUCCESS;
        }
        return  SystemConstant.UPDATE_ERROR;
    }


    @RequestMapping("/checkGoodTypeHasChild")
    public String checkGoodTypeHasChild(int id){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<GoodsType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        if (goodsTypeService.count(queryWrapper)>0){
            map.put(SystemConstant.EXIST,true);
            map.put(SystemConstant.MESSAGE,"对不起，当前部门下有子节点，无法删除");
        }
        map.put(SystemConstant.EXIST,false);

        return JSON.toJSONString(map);
    }


    @RequestMapping("/deleteGoodsType")
    public JSONResult deleteGoodsType(int id){
        if (goodsTypeService.removeById(id)){
            return SystemConstant.DELETE_SUCCESS;
        }
        return SystemConstant.DELETE_ERROR;

    }
}

