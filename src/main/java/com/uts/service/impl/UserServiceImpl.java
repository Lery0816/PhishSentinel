package com.uts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uts.enums.ErrorCode;
import com.uts.exception.BusinessException;
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
    public void createUser(User user) throws BusinessException {
        if (userMapper.countByEmail(user.getEmail()) > 0) {
            throw new BusinessException(ErrorCode.EMAIL_EXISTS.getCode(),
                    ErrorCode.EMAIL_EXISTS.getMessage());
        }
        if (userMapper.countByUserName(user.getUsername()) > 0) {
            throw new BusinessException(ErrorCode.USERNAME_DUPLICATE.getCode(),
                    ErrorCode.USERNAME_DUPLICATE.getMessage());
        }
        userMapper.insert(user);
    }

    @Override
    public User findUser(String email, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email).eq("password", password);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void updateUser(User user) {
        if (user.getId() == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND.getCode(), "User ID cannot be null");
        }
        int rows = userMapper.updateById(user);
        if (rows != 1) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR.getCode(), "Failed to update user");
        }
    }
}
