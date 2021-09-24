package com.bdqn.sys.service.impl;

import com.bdqn.sys.entity.Permission;
import com.bdqn.sys.dao.PermissionMapper;
import com.bdqn.sys.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-17
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public boolean removeById(Serializable id) {
        //根据菜单id或权限id删除sys_role_permission权限菜单关系表数据
        permissionMapper.deleteRolePermissionByPid(id);
        return super.removeById(id);
    }

    @Override
    public List<Integer> findRolePermissionByRoleId(int roleId) {
        return permissionMapper.findRolePermissionByRoleId(roleId);
    }
}
