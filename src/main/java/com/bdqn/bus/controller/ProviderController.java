package com.bdqn.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.bus.entity.Customer;
import com.bdqn.bus.entity.Provider;
import com.bdqn.bus.service.ProviderService;
import com.bdqn.bus.vo.ProviderVo;
import com.bdqn.common.utils.DataGridViewResult;
import com.bdqn.common.utils.JSONResult;
import com.bdqn.common.utils.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-22
 */
@RestController
@RequestMapping("/bus/provider")
public class ProviderController {

    @Resource
    private ProviderService providerService;


    @RequestMapping("/providerList")
    public DataGridViewResult providerList(ProviderVo providerVo){

        IPage<Provider> page = new Page<Provider>(providerVo.getPage(),providerVo.getLimit());

        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getProvidername()),"providername",providerVo.getProvidername());
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getTelephone()),"telephone",providerVo.getTelephone());
        queryWrapper.like(StringUtils.isNotBlank(providerVo.getLinkman()),"linkman",providerVo.getLinkman());

        providerService.page(page,queryWrapper);

        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }


    @RequestMapping("/addProvider")
    public JSONResult addProvider(Provider provider){
        if (providerService.save(provider)){
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }


    @RequestMapping("/deleteById")
    public JSONResult deleteById(int id){
        if (providerService.removeById(id)){
            return SystemConstant.DELETE_SUCCESS;
        }
        return SystemConstant.DELETE_ERROR;
    }


    @RequestMapping("/batchDelete")
    public JSONResult batchDelete(String ids){
        String[] idArr = ids.split(",");
        if (providerService.removeByIds(Arrays.asList(idArr))){
            return SystemConstant.DELETE_SUCCESS;
        }
        return SystemConstant.DELETE_ERROR;

    }

}

