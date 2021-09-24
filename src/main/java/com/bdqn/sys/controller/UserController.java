package com.bdqn.sys.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.sys.entity.Log;
import com.bdqn.sys.entity.User;
import com.bdqn.sys.service.LogService;
import com.bdqn.sys.service.RoleService;
import com.bdqn.sys.service.UserService;
import com.bdqn.common.utils.*;
import com.bdqn.sys.vo.LoginUserVo;
import com.bdqn.sys.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-16
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Resource
    private LogService logService;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @RequestMapping("/login")
    public JSONResult login(String loginname, String pwd, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginname, pwd);
        try {
            subject.login(token);
            LoginUserVo loginUserVo = (LoginUserVo) subject.getPrincipal();
            request.getSession().setAttribute(SystemConstant.LOGINUSER, loginUserVo.getUser());
            //保存日志
            Log log = new Log("用户登录",
                    SystemConstant.LOGIN_ACTION,
                    loginUserVo.getUser().getLoginname() + "-" + loginUserVo.getUser().getName(),
                    loginUserVo.getUser().getId(),
                    request.getRemoteAddr(),
                    new Date());
            logService.save(log);
            return SystemConstant.LOGIN_SUCCESS;
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return SystemConstant.LOGIN_ERROR_PASS;
    }


    @RequestMapping("/userList")
    public DataGridViewResult userList(UserVo userVo) {
        IPage<User> page = new Page<User>(userVo.getPage(), userVo.getLimit());
        try {
            IPage<User> userIPage = userService.findUserListByPage(page, userVo);
            return new DataGridViewResult(userIPage.getTotal(), userIPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/loadUserByDeptId")
    public DataGridViewResult loadUserByDeptId(Integer deptId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("type", SystemConstant.NORMAL_USER);
        queryWrapper.eq(deptId != null, "deptid", deptId);
        List<User> users = userService.list(queryWrapper);
        return new DataGridViewResult(users);
    }


    //验证用户名是否存在
    @RequestMapping("/checkLoginName")
    public String checkLoginName(String loginName) {
        Map<String, Object> map = new HashMap<String, Object>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname", loginName);
        int count = userService.count(queryWrapper);
        if (count > 0) {
            map.put(SystemConstant.EXIST, true);
            map.put(SystemConstant.MESSAGE, "登录名称已存在，请重新输入！");
        } else {
            map.put(SystemConstant.EXIST, false);
            map.put(SystemConstant.MESSAGE, "登录名称可以使用！");
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping("/addUser")
    public JSONResult addUser(User user) {
        user.setHiredate(new Date());
        String salt = UUIDUtil.randomUUID();
        user.setLoginpwd(PasswordUtil.md5(SystemConstant.DEFAULT_PWD, salt, SystemConstant.HASHITERATION));
        user.setSalt(salt);
        user.setType(SystemConstant.NORMAL_USER);
        user.setImgpath("defaultImage.jpg");

        if (userService.save(user)) {
            return SystemConstant.ADD_SUCCESS;
        }
        return SystemConstant.ADD_ERROR;
    }


    @RequestMapping("/loadUserById")
    public DataGridViewResult loadUserById(Integer id) {
        return new DataGridViewResult(userService.getById(id));
    }


    @RequestMapping("/updateUser")
    //调用修改用户方法
    public JSONResult updateById(User user) {
        if (userService.updateById(user)) {
            return SystemConstant.UPDATE_SUCCESS;
        }
        return SystemConstant.UPDATE_ERROR;
    }


    @RequestMapping("/deleteUserById")
    //调用修改用户方法
    public JSONResult deleteById(int id) {
        if (userService.removeById(id)) {
            return SystemConstant.DELETE_SUCCESS;
        }
        return SystemConstant.DELETE_SUCCESS;
    }

    @RequestMapping("/resetPwd")
    public JSONResult resetPwd(int id){
        String salt = UUIDUtil.randomUUID();
        User user = new User();
        user.setId(id);
        user.setSalt(salt);
        user.setLoginpwd(PasswordUtil.md5(SystemConstant.DEFAULT_PWD,salt,SystemConstant.HASHITERATION));
        if (userService.updateById(user)) {
            return SystemConstant.RESET_SUCCESS;
        }
        return SystemConstant.RESET_ERROR;
    }


    @RequestMapping("/initRoleByUserId")
    public DataGridViewResult initRoleByUserId(int id){
        List<Map<String,Object>> mapList = roleService.listMaps();
        Set<Integer> roleIdsList = userService.findUserRoleByUserId(id);
        for (Map<String, Object> objectMap : mapList) {
            boolean flag = false;
            int roleId = (int)objectMap.get("id");
            for (Integer rid : roleIdsList) {
                if (rid==roleId){
                    flag = true;
                    break;
                }
            }
            objectMap.put("LAY_CHECKED",flag);
        }

        return new DataGridViewResult(Long.valueOf(mapList.size()),mapList);
    }


    @RequestMapping("/saveUserRole")
    public JSONResult saveUserRole(int userId,String roleIds){
        try {
            if (userService.saveUserRole(userId,roleIds)){
                return SystemConstant.DISTRIBUTE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  SystemConstant.DISTRIBUTE_ERROR;
    }
}

