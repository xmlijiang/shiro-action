package com.jali.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * @author lijiang
 * @create 2020-05-13 17:16
 */
public class DatabaseRealm extends AuthorizingRealm {

    /**
     * 认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 能进入到这里，表示账号已经通过验证了
        String userName = (String) principalCollection.getPrimaryPrincipal();
        // 通过DAO获取角色和权限
        Set<String> roles = new DAO().listRoles(userName);
        Set<String> permissions = new DAO().listPermissions(userName);

        // 授权对象
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        // 把通过DAO获取到的角色和权限放进去
        s.setRoles(roles);
        s.setStringPermissions(permissions);

        return s;
    }

    /**
     * 授权
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取账号密码
        UsernamePasswordToken t = (UsernamePasswordToken) token;
        String userName = token.getPrincipal().toString();
        String password = new String(t.getPassword());
        // 获取数据库中的密码
        String passwordInDB = new DAO().getPassword(userName);
        // 如果为空就是账号不存在，如果不相同就是密码错误，但是都抛出AuthenticationException，而不是抛出具体错误原因，免得给破解者提供帮助信息
        if(null==passwordInDB || !passwordInDB.equals(password)){
            throw new AuthenticationException();
        }
        // 认证信息里存放账号密码，getName()是当前Realm的继承方法，通常返回当前类名：databaseRealm
        SimpleAuthenticationInfo s = new SimpleAuthenticationInfo(userName, password, getName());
        return s;
    }
}

















