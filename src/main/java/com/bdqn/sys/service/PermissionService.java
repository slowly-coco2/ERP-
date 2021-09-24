package com.bdqn.sys.service;

import com.bdqn.sys.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-17
 */
public interface PermissionService extends IService<Permission> {

    List<Integer> findRolePermissionByRoleId(int roleId);
}
