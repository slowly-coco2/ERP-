package com.bdqn.bus.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bdqn.bus.entity.Leavebill;
import com.bdqn.bus.dao.LeavebillMapper;
import com.bdqn.bus.service.LeavebillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bdqn.bus.vo.LeavebillVo;
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
public class LeavebillServiceImpl extends ServiceImpl<LeavebillMapper, Leavebill> implements LeavebillService {

    @Resource
    private LeavebillMapper leavebillMapper;

    @Override
    public IPage<Leavebill> findLeaveBillListByPage(IPage<Leavebill> page, LeavebillVo leavebillVo) throws Exception {
        return leavebillMapper.findLeaveBillListByPage(page,leavebillVo);
    }
}
