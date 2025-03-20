package com.uts.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uts.enums.ErrorType;
import com.uts.exception.UserException;
import com.uts.mapper.UserMapper;
import com.uts.pojo.User;
import com.uts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public void createUser(User user) throws UserException {
        if (userMapper.countByEmail(user.getEmail()) > 0) {
            throw new UserException("Email already exists",ErrorType.EMAIL_DUPLICATE);
        }
        if (userMapper.countByUserName(user.getUsername()) > 0) {
            throw new UserException("Username already exists",ErrorType.USERNAME_DUPLICATE);
        }
        userMapper.insert(user);
    }

    @Override
    public boolean findUser(String username, String password) {
        if(userMapper.findUser(username,password)==0){
            return false;
        }
        return true;
    }
}
