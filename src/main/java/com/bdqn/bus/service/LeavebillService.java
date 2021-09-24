package com.bdqn.bus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bdqn.bus.entity.Leavebill;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bdqn.bus.vo.LeavebillVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-23
 */
public interface LeavebillService extends IService<Leavebill> {

    IPage<Leavebill> findLeaveBillListByPage(IPage<Leavebill> page, LeavebillVo leavebillVo) throws  Exception;
}
