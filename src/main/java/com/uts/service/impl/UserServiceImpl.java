package com.uts.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uts.mapper.UserMapper;
import com.uts.pojo.User;
import com.uts.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
