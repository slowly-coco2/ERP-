package com.bdqn.sys.dao;

import com.bdqn.sys.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author KazuGin
 * @since 2021-09-21
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Delete("delete from sys_role_user where rid=#{id}")
    void deleteRoleUserByRoleId(Serializable id);

    @Delete("delete from sys_role_permission where rid=#{id}")
    void deleteRolePermissionByRoleId(Serializable id);

    @Insert("insert into sys_role_permission (rid,pid) values(#{rid},#{pid})")
    void insertRolePermission(@Param("rid") int rid, @Param("pid") String pid) throws Exception;

    @Select("select pid from sys_role_permission where rid=#{roleId}")
    Set<Integer> findRolePermissionByRoleId(Integer roleId) throws Exception;
}
