package com.xh.website.module.user.controller;

import com.xh.website.module.user.entity.User;
import com.xh.website.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author xinghao
 * @descreption
 * @date 2018/12/19
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    MyUserDetailService userDetailsService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public String addUser(User user){
        if (user.getPassWord().length()<6){
            return "redirect:/user/signup?passwordErr";
        }
        try {
            userDetailsService.addUser(user);
        } catch (Exception e){
            return "redirect:/user/signup?usernameErr";
        }

        return "redirect:/user/login?signup_success";
    }
}
