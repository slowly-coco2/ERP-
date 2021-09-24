package com.bdqn.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bdqn.sys.entity.User;
import com.bdqn.sys.dao.UserMapper;
import com.bdqn.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bdqn.sys.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-16
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findUserByName(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("loginname",userName);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<User> findUserListByPage(IPage<User> page, UserVo userVo) {
        return userMapper.findUserListByPage(page,userVo);
    }

    @Override
    public Set<Integer> findUserRoleByUserId(int id) {
        return userMapper.findUserRoleByUserId(id);
    }

    @Override
    public boolean saveUserRole(int userId, String roleIds) throws Exception {
        try {
            //先删除sys_role_user表的数据
            userMapper.deleteUserRoleByUerId(userId);
            //再添加sys_role_user表的数据
            String[] rids = roleIds.split(",");

            for (int i = 0; i < rids.length; i++) {
                userMapper.insertUserRole(userId,rids[i]);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
