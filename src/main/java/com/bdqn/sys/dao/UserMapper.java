package com.bdqn.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bdqn.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bdqn.sys.vo.UserVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-16
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> findUserListByPage(@Param("page") IPage<User> page, @Param("user") UserVo userVo);


    @Select("SELECT rid FROM sys_role_user WHERE uid = #{uid}")
    Set<Integer> findUserRoleByUserId(int id);

    @Delete("delete from sys_role_user where uid = #{userId}")
    void deleteUserRoleByUerId(int userId) throws Exception;

    @Insert("insert into sys_role_user (rid,uid) values (#{rid},#{uid})")
    void insertUserRole(@Param("uid") int userId,@Param("rid") String rid) throws Exception;
}
