package com.realm;

import com.pojo.Permission;
import com.pojo.Role;
import com.pojo.User;
import com.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // 从身份认证信息AuthenticationToken获取授权认证信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 从AuthenticationToken是PrincipalCollection
        User user = (User)principals.getPrimaryPrincipal();
        // User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissionList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        Set<Role> roleSet = user.getRoles();
        if (CollectionUtils.isNotEmpty(roleSet)) {
            for(Role role : roleSet) {
                roleNameList.add(role.getRname());
                Set<Permission> permissionSet = role.getPermissions();
                if (CollectionUtils.isNotEmpty(permissionSet)) {
                    for (Permission permission : permissionSet) {
                        permissionList.add(role.getRname() + ":" + permission.getName());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        info.addRoles(roleNameList);
        return info;
    }

    // 从数据库获取身份认证信息AuthenticationToken，shiro会对比用户UsernamepasswordToken与AuthenticationToken
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        User user = userService.getByUsername(username);

        // 找不到对应用户
        if(user == null){
            throw new UnknownAccountException("不存在的用户");
        }

        // 从数据库取出的明文进行md5加密，再作为认证信息
        SimpleHash sh = new SimpleHash("MD5",user.getPassword(),null,1024);


        // 返回认证信息AuthenticationToken
        // 用于认证的用户名 用于认证的用户名 自定义Realm的classname
        return new SimpleAuthenticationInfo(user, sh, this.getClass().getName());
    }
}
