package com.dao;

import com.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Author:ztian
 * @Description:
 * @CreateTime: 2018/3/4  11:54
 */
public interface UserMapper {
    User getByUsername(@Param("username") String username);
}
