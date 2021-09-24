package com.bdqn.bus.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.bus.entity.Goods;
import com.bdqn.bus.service.GoodsService;
import com.bdqn.bus.vo.GoodsVo;
import com.bdqn.common.utils.DataGridViewResult;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-23
 */
@RestController
@RequestMapping("/bus/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping("/goodsList")
    public DataGridViewResult goodsList(GoodsVo goodsVo){
        IPage<Goods> page = new Page<Goods>(goodsVo.getPage(), goodsVo.getLimit());
        try {
            IPage<Goods> GoodsIPage = goodsService.findGoodsListByPage(page,goodsVo);
            return new DataGridViewResult(GoodsIPage.getTotal(), GoodsIPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

