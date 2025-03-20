package com.uts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uts.exception.UserException;
import com.uts.pojo.User;

public interface UserService extends IService<User> {
    void createUser(User user) throws UserException;
    boolean findUser(String username,String password);
}
