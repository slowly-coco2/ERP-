package com.bdqn.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bdqn.sys.entity.Permission;
import com.bdqn.sys.entity.User;
import com.bdqn.sys.service.PermissionService;
import com.bdqn.sys.service.RoleService;
import com.bdqn.sys.service.UserService;
import com.bdqn.common.utils.SystemConstant;
import com.bdqn.sys.vo.LoginUserVo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    /**
     * UserRealm的域名
     * @return
     */
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        LoginUserVo loginUserVo = (LoginUserVo) principalCollection.getPrimaryPrincipal();
        Set<String> permissions = loginUserVo.getPermissions();
        if (loginUserVo.getUser().getType()== SystemConstant.SUPERUSER){
            simpleAuthorizationInfo.addStringPermission("*:*");
        }else{
            if (permissions!=null && permissions.size()>0){
                simpleAuthorizationInfo.addStringPermissions(permissions);

            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取当前登录主体
        String userName = (String) authenticationToken.getPrincipal();
        try {
            //根据用户名查询用户信息的方法
            User user = userService.findUserByName(userName);
            //对象不为空
            if(user!=null){
                //创建当前登录用户对象
                //创建登录用户对象，传入用户信息，角色列表，权限列表
                LoginUserVo loginUserVo = new LoginUserVo(user,null,null);

                QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
                queryWrapper.eq("type",SystemConstant.TYPE_PERMISSION);

                Set<Integer> currentUserRoleIds = userService.findUserRoleByUserId(user.getId());
                Set<Integer> pids = new HashSet<Integer>();
                for (Integer roleId : currentUserRoleIds) {
                    Set<Integer> permissionIds = roleService.findRolePermissionByRoleId(roleId);
                    pids.addAll(permissionIds);
                }

                List<Permission> list = new ArrayList<Permission>();
                if (pids!=null && pids.size()>0){
                    queryWrapper.in("id",pids);
                    list = permissionService.list(queryWrapper);
                }
                Set<String> percodes = new HashSet<String>();
                for (Permission permission : list) {
                    percodes.add(permission.getPercode());
                }

                loginUserVo.setPermissions(percodes);






                //创建盐值(以用户名作为盐值)
                ByteSource salt = ByteSource.Util.bytes(user.getSalt());
                //创建身份验证对象
                //参数1：当前登录对象  参数2：密码  参数3：盐值 参数4：域名
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUserVo,user.getLoginpwd(),salt,getName());
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //验证失败
        return null;
    }
}