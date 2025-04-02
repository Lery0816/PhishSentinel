package com.uts.controller;

import com.uts.pojo.User;
import com.uts.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @ResponseBody
    public int SignUp(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userService.createUser(user);
        return 0; // Registration successful
    }

    @PostMapping("/login")
    @ResponseBody
    public int login(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        User user=userService.findUser(email,password);
        if (null!=user) {
            User sessionUser = new User();
            sessionUser.setId(user.getId());
            request.getSession().setAttribute("user", user);
            return 1; // Login successful
        }
        return 0; // Login failed
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
