package com.uts.controller;

import com.uts.pojo.User;
import com.uts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    @ResponseBody
    public List<User> test01(){
        List<User> usetList=userService.list();
        return usetList;
    }
}
