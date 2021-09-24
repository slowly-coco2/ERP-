package com.bdqn.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.bus.entity.Customer;
import com.bdqn.bus.service.CustomerService;
import com.bdqn.bus.vo.CustomerVo;
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
@RequestMapping("/bus/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @RequestMapping("/customerList")
    public DataGridViewResult customerList(CustomerVo customerVo){
        IPage<Customer> page = new Page<Customer>(customerVo.getPage(),customerVo.getLimit());

        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getCustomername()),"customername",customerVo.getCustomername());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getTelephone()),"telephone",customerVo.getTelephone());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getLinkman()),"linkman",customerVo.getLinkman());

        customerService.page(page,queryWrapper);

        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }


    @RequestMapping("/addCustomer")
    public JSONResult addCustomer(Customer customer){
        if (customerService.save(customer)){
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }


    @RequestMapping("/updateCustomer")
    public JSONResult updateCustomer(Customer customer){
        if (customerService.updateById(customer)){
            return SystemConstant.UPDATE_SUCCESS;
        }
        return SystemConstant.UPDATE_ERROR;
    }

    @RequestMapping("/deleteById")
    public JSONResult deleteById(int id){
        if (customerService.removeById(id)){
            return SystemConstant.DELETE_SUCCESS;
        }
        return SystemConstant.DELETE_ERROR;
    }

    @RequestMapping("/batchDelete")
    public JSONResult batchDelete(String ids){
        String[] idArr = ids.split(",");
        if (customerService.removeByIds(Arrays.asList(idArr))){
            return SystemConstant.DELETE_SUCCESS;
        }
        return SystemConstant.DELETE_ERROR;
    }


}

