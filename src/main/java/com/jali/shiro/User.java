package com.jali.shiro;

import lombok.*;

/**
 * @author lijiang
 * @create 2020-05-13 9:21
 */
@Setter
@Getter
public class User {

    private int id;
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
