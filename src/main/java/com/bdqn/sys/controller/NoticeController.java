package com.bdqn.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.sys.entity.Notice;
import com.bdqn.sys.entity.User;
import com.bdqn.sys.service.NoticeService;
import com.bdqn.common.utils.DataGridViewResult;
import com.bdqn.common.utils.JSONResult;
import com.bdqn.common.utils.SystemConstant;
import com.bdqn.sys.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-18
 */
@RestController
@RequestMapping("/sys/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    @RequestMapping("/noticeList")
    public DataGridViewResult findNoticeList(NoticeVo noticeVo) {
        //创建分页对象，并指定当前页码及每页显示数量
        IPage<Notice> page = new Page<Notice>(noticeVo.getPage(), noticeVo.getLimit());
        //设置查询条件
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<Notice>();
        //发布人
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOpername()), "opername", noticeVo.getOpername());
        //标题
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()), "title", noticeVo.getTitle());
        //开始时间
        queryWrapper.ge(noticeVo.getStartTime() != null, "createtime", noticeVo.getStartTime());
        //结束时间
        queryWrapper.le(noticeVo.getEndTime() != null, "createtime", noticeVo.getEndTime());
        //设置排序
        queryWrapper.orderByDesc("createtime");//登录时间降序
        //调用分页查询方法
        noticeService.page(page, queryWrapper);
        //返回数据
        return new DataGridViewResult(page.getTotal(), page.getRecords());
    }

    @PostMapping("/addNotice")
    public JSONResult addNotice(Notice notice, HttpSession session){
        User user = (User) session.getAttribute(SystemConstant.LOGINUSER);
        notice.setCreatetime(new Date());
        notice.setOpername(user.getName());
        if (noticeService.save(notice)){
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }
    @PostMapping("/updateNotice")
    public JSONResult updateNotice(Notice notice, HttpSession session){
       notice.setModifytime(new Date());
        if (noticeService.updateById(notice)){
            return SystemConstant.UPDATE_SUCCESS;
        }
        return SystemConstant.UPDATE_ERROR;
    }


    @RequestMapping("/deleteById")
    public JSONResult deleteById(int id){
        if (noticeService.removeById(id)){
            return SystemConstant.DELETE_SUCCESS;
        }
        return SystemConstant.DELETE_ERROR;
    }


    @RequestMapping("/batchDelete")
    public JSONResult deleteById(String ids){
        try {
            String [] idsStr = ids.split(",");
            if (noticeService.removeByIds(Arrays.asList(idsStr))){
                return SystemConstant.DELETE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SystemConstant.DELETE_ERROR;
    }
}

