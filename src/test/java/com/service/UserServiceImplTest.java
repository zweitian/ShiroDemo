package com.service;

import base.Junit4BaseClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Author:ztian
 * @Description:
 * @CreateTime: 2018/3/4  13:52
 */
public class UserServiceImplTest extends Junit4BaseClass {
    @Autowired
    private UserService userService;
    @Test
    public void getByUsername() {
        System.out.println(userService.getByUsername("admin"));
    }
}