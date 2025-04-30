package com.uts.controller;

import com.uts.pojo.User;
import com.uts.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
        User user = userService.findUser(email, password);
        if (user != null) {
            user.setPassword(null); // Remove password before storing in session
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

    @GetMapping("/current")
    @ResponseBody
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute("user");
        if (userObj instanceof User) {
            return (User) userObj;
        }
        return null;
    }

    @PutMapping("/update")
    @ResponseBody
    public int updateUserProfile(@RequestBody User user, HttpServletRequest request) {
        User currentUser = getCurrentUser(request);
        if (currentUser == null) {
            return 0; // Not logged in
        }

        // Update only allowed fields
        currentUser.setFullName(user.getFullName());
        currentUser.setCompany(user.getCompany());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setUpdatedAt(LocalDateTime.now());

        try {
            userService.updateUser(currentUser);
            // Update session with new user data
            request.getSession().setAttribute("user", currentUser);
            return 1; // Update successful
        } catch (Exception e) {
            return 0; // Update failed
        }
    }
}
