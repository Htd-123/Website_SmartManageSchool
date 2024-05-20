package org.example.smartmanageschool.controller;

import org.example.smartmanageschool.Repository.login_Repo;
import org.example.smartmanageschool.model.login_model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class signup_controller {
    //Render ra trang signup
    @GetMapping("signup")
    public String show_signup() {
        return "signup_view";
    }

    @Autowired
    login_Repo login_repo;

    //Handle chuc nang signup
    @PostMapping("signup")
    public String handle_signup(@RequestParam("username") String username, @RequestParam("password") String password,
                                @RequestParam("password_repeat") String password_repeat) {
        if(password.equals(password_repeat)){
            login_model loginModel = new login_model();
            loginModel.setUsername(username);
            loginModel.setPassword(password);
            loginModel.setDecentralization(2);
            login_repo.save(loginModel);
            return "redirect:/login";
        } else {
            return "redirect:/signup";
        }
    }
}
