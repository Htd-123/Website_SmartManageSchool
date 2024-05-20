package org.example.smartmanageschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class profile_controller {
    @GetMapping("/profile")
    public String render_profile() {
        return "profile_view";
    }
}
