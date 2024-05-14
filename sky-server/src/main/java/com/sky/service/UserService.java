package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @author Moyu
 * @version 1.0
 * @description: TODO
 * @date 2024/5/13 16:47
 */
public interface UserService {

    public User login(UserLoginDTO userLoginDTO);
}
