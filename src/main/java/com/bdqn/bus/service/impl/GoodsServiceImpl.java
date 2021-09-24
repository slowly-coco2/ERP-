package com.bdqn.bus.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bdqn.bus.entity.Goods;
import com.bdqn.bus.dao.GoodsMapper;
import com.bdqn.bus.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bdqn.bus.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-23
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public IPage<Goods> findGoodsListByPage(IPage<Goods> page, GoodsVo goodsVo) {
        return goodsMapper.findGoodsListByPage(page,goodsVo);
    }
}
