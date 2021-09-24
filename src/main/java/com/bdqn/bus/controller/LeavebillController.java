package com.bdqn.bus.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.bus.entity.Leavebill;
import com.bdqn.bus.service.LeavebillService;
import com.bdqn.bus.vo.LeavebillVo;
import com.bdqn.common.utils.DataGridViewResult;
import com.bdqn.common.utils.JSONResult;
import com.bdqn.common.utils.SystemConstant;
import com.bdqn.sys.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-23
 */
@RestController
@RequestMapping("/bus/leavebill")
public class LeavebillController {

    @Resource
    private LeavebillService leavebillService;

    @RequestMapping("/leavebillList")
    public DataGridViewResult leavebillList(LeavebillVo leavebillVo, HttpSession session){

        User user = (User) session.getAttribute(SystemConstant.LOGINUSER);
        leavebillVo.setUserid(user.getId());

        IPage<Leavebill> page = new Page<>(leavebillVo.getPage(),leavebillVo.getLimit());
        try {
            leavebillService.findLeaveBillListByPage(page,leavebillVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DataGridViewResult(page.getTotal(),page.getRecords());
    }


    @RequestMapping("/addLeavebill")
    public JSONResult addLeavebill(Leavebill leavebill,HttpSession session){
        User user = (User) session.getAttribute(SystemConstant.LOGINUSER);
        leavebill.setUserid(user.getId());
        leavebill.setCreatetime(new Date());
        leavebill.setCheckPerson(user.getMgr());
        if (leavebill.getState()==SystemConstant.LEAVE_CREATE_STETA){
            leavebill.setState(SystemConstant.LEAVE_CREATE_STETA);
        }else if (leavebill.getState()==SystemConstant.LEAVE_CHECKING_STETA){
            leavebill.setState(SystemConstant.LEAVE_CHECKING_STETA);
            leavebill.setCommittime(new Date());
        }
        leavebill.setCheckPerson(user.getMgr());



        if (leavebillService.save(leavebill)) {
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }

    @RequestMapping("/updateLeavebill")
    public JSONResult updateLeavebill(Leavebill leavebill){
        if (leavebill.getState()==SystemConstant.LEAVE_CREATE_STETA){
            leavebill.setState(SystemConstant.LEAVE_CREATE_STETA);
        }else if (leavebill.getState()==SystemConstant.LEAVE_CHECKING_STETA){
            leavebill.setState(SystemConstant.LEAVE_CHECKING_STETA);
            leavebill.setCommittime(new Date());
        }



        if (leavebillService.updateById(leavebill)) {
            return SystemConstant.UPDATE_SUCCESS;
        }
        return SystemConstant.UPDATE_ERROR;
    }
}

