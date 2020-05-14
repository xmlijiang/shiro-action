package com.jali.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @author lijiang
 * @create 2020-05-14 15:37
 */
public class TestShiroMd5 {

    public static void main(String[] args) {

        User user = new User("tom","123");
        User user2 = new User("zhang3","12345");
        User user3 = new User("li4","abcde");
        if(login(user)){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }
        if(login(user2)){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }
        if(login(user3)){
            System.out.println("登录成功");
        }else{
            System.out.println("登录失败");
        }
    }

    private static boolean hasRole(User user, String role) {
        Subject subject = getSubject(user);
        return subject.hasRole(role);
    }

    private static boolean isPermitted(User user, String permit) {
        Subject subject = getSubject(user);
        return subject.isPermitted(permit);
    }

    /**
     * isAuthenticated 是否已认证
     * Realm 领域，范围，王国
     * @param user
     * @return
     */
    private static boolean login(User user){
        Subject subject = getSubject(user);
        // 如果已经登录过了，退出
        if(subject.isAuthenticated()){
            subject.logout();
        }
        // 封装用户的数据
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(),user.getPassword());

        try{
            // 将用户的数据token最终传递到Realm中进行对比
            subject.login(token);
        }catch (AuthenticationException e){
            // 验证错误
            return false;
        }
        return subject.isAuthenticated();
    }

    private static Subject getSubject(User user) {
        // 加载配置文件，并获取工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 获取安全管理者实例
        SecurityManager sm = factory.getInstance();
        // 将安全管理者放入全局对象
        SecurityUtils.setSecurityManager(sm);
        // 全局对象通过安全管理者生成 Subject 对象
        return SecurityUtils.getSubject();
    }
}
