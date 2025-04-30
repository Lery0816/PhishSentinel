package com.uts.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.uts.pojo.User;

public interface UserService extends IService<User> {
    void createUser(User user);
    User findUser(String email, String password);
    void updateUser(User user);
}
