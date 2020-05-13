package com.jali.shiro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lijiang
 * @create 2020-05-13 9:21
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String name;
    private String password;

}
