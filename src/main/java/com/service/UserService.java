package com.service;

import com.pojo.User;

/**
 * @Author:ztian
 * @Description:
 * @CreateTime: 2018/3/4  11:57
 */
public interface UserService {
    User getByUsername(String username);
}
