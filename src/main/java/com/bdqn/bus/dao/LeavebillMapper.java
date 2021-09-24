package com.bdqn.bus.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bdqn.bus.entity.Leavebill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bdqn.bus.vo.LeavebillVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-23
 */
public interface LeavebillMapper extends BaseMapper<Leavebill> {
    IPage<Leavebill> findLeaveBillListByPage(@Param("page") IPage<Leavebill> page, @Param("leavebill") LeavebillVo leavebillVo) throws  Exception;
}
