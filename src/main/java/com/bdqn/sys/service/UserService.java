package com.bdqn.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bdqn.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bdqn.sys.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-16
 */
public interface UserService extends IService<User> {
    User findUserByName(String userName);

    IPage<User> findUserListByPage(IPage<User> page,UserVo userVo);

    Set<Integer> findUserRoleByUserId(int id);

    boolean saveUserRole(int userId, String roleIds) throws Exception;
}
