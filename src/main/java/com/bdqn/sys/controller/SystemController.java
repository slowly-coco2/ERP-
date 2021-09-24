package com.bdqn.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys")
public class SystemController {

    /**
     * 去到后台首页
     * @return
     */
    @RequestMapping("/index")
    public String toIndex(){
        return "system/home/index";
    }
    @RequestMapping("/toDeskManager")
    public String toDeskManager(){
        return "system/home/desktopManager";
    }

    @RequestMapping("/toLogManager")
    public String toLogManager(){
        return "system/log/logManager";
    }

    @RequestMapping("/toNoticeManager")
    public String toNoticeManager(){
        return "system/notice/noticeManager";
    }

    @RequestMapping("/toDeptLeft")
    public String toDeptLeft(){
        return "system/dept/left";
    }

    @RequestMapping("/toDeptRight")
    public String toDeptRight(){
        return "system/dept/right";
    }

    @RequestMapping("/toDeptManager")
    public String toDeptManager(){
        return "system/dept/deptManager";
    } 
    

    @RequestMapping("/toMenuManager")
    public String toMenuManager() {
        return "system/menu/menuManager";
    }

    @RequestMapping("/toMenuLeft")
    public String toMenuLeft() {
        return "system/menu/left";
    }

    @RequestMapping("/toMenuRight")
    public String toMenuRight() {
        return "system/menu/right";
    }

    @RequestMapping("/toPermissionManager")
    public String toPermissionManager() {
        return "system/permission/permissionManager";
    }

    @RequestMapping("/toPermissionLeft")
    public String toPermissionLeft() {
        return "system/permission/left";
    }

    @RequestMapping("/toPermissionRight")
    public String toPermissionRight() {
        return "system/permission/right";
    }

    @RequestMapping("/toRoleManager")
    public String toRoleManager() {
        return "system/role/roleManager";
    }

    @RequestMapping("/toUserManager")
    public String toUserManager() {
        return "system/user/userManager";
    }

    @RequestMapping("/toUserLeft")
    public String toUserLeft() {
        return "system/user/left";
    }

    @RequestMapping("/toUserRight")
    public String toUserRight() {
        return "system/user/right";
    }
}