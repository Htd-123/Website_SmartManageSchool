package org.example.smartmanageschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.smartmanageschool.Repository.login_Repo;
import org.example.smartmanageschool.model.login_model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class login_controller {
    //Render trang login
    @GetMapping("/login")
    public String show_login() {
        return "login_view";
    }

    @Autowired
    login_Repo login_repo;

    //Handle chuc nang dang nhap
    @PostMapping("enterlogin")
    public String handle_login(@RequestParam("user") String username, @RequestParam("pass") String password, HttpServletRequest request) {
        login_model loginModel = login_repo.findByUsername(username);

        if (loginModel != null && loginModel.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            return "redirect:/homepage";
        } else {
            return "redirect:/login";
        }
    }
}