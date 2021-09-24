package com.bdqn.bus.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bdqn.bus.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bdqn.bus.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-23
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage<Goods> findGoodsListByPage(@Param("page") IPage<Goods> page, @Param("goods") GoodsVo goodsVo);
}
