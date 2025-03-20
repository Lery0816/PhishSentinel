package com.uts.controller;

import com.uts.enums.ErrorType;
import com.uts.exception.UserException;
import com.uts.pojo.User;
import com.uts.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/user")
    @ResponseBody
    public int SignUp(@RequestParam String username,@RequestParam String password,@RequestParam String email){
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        try {
            userService.createUser(user);
            return 0;//Registration successful
        } catch (UserException e) {
            if (e.getErrorType()== ErrorType.EMAIL_DUPLICATE){
                return 1;//Duplicate email
            }
            if (e.getErrorType()== ErrorType.USERNAME_DUPLICATE){
                return 2;//Duplicate username
            }
            return 3;//Other errors
        }
    }
    @PostMapping("/login")
    @ResponseBody
    public int login(@RequestParam String username,@RequestParam String password, HttpServletRequest request){
        if (userService.findUser(username,password)){
            User user=new User();
            user.setUsername(username);
            request.getSession().setAttribute("user",user);
            return 1;//login successful
        }
        return 0;//login failed
    }
}
