package com.bdqn.sys.service;

import com.bdqn.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-21
 */
public interface RoleService extends IService<Role> {

    //保存分配权限关系
    boolean saveRolePermission(int rid, String ids) throws Exception;

    Set<Integer> findRolePermissionByRoleId(Integer roleId) throws Exception;
}
