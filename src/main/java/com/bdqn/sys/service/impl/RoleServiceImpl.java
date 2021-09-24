package com.bdqn.sys.service.impl;

import com.bdqn.sys.entity.Role;
import com.bdqn.sys.dao.RoleMapper;
import com.bdqn.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Override
    public boolean removeById(Serializable id) {
        roleMapper.deleteRoleUserByRoleId(id);
        roleMapper.deleteRolePermissionByRoleId(id);
        return super.removeById(id);
    }

    @Override
    public boolean saveRolePermission(int rid, String ids) throws Exception {
        try {
            //新删除原来的数据
            roleMapper.deleteRolePermissionByRoleId(rid);
            // 再保存新的关系数据
            //拆分权限id字符串
            String[] pids = ids.split(",");
            //System.out.println(pids);
            for (int i = 0; i < pids.length; i++) {
                roleMapper.insertRolePermission(rid,pids[i]);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Set<Integer> findRolePermissionByRoleId(Integer roleId) throws Exception {

        return roleMapper.findRolePermissionByRoleId(roleId);
    }
}
