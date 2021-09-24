package com.bdqn.bus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bdqn.bus.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bdqn.bus.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-23
 */
public interface GoodsService extends IService<Goods> {

    IPage<Goods> findGoodsListByPage(IPage<Goods> page, GoodsVo goodsVo);
}
