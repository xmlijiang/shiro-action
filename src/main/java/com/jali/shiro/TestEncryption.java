package com.jali.shiro;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author lijiang
 * @create 2020-05-14 14:02
 */
public class TestEncryption {

    public static void main(String[] args) {

        // md5加盐
        String password = "123";
        // 随机一个盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 加密此时
        int times = 2;
        // 算法名称
        String algorithmName = "md5";

        // 加密后的密码
        String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();
        System.out.printf("原始密码是%s，盐：%s，运算次数：%d，加密后的密文：%s",password,salt,times,encodedPassword);
        // 不安全的加密
//        String password = "123";
//        String encodedPassword = new Md5Hash(password).toString();
//        System.out.println(encodedPassword);
    }
}
