package org.example.smartmanageschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dashboard_controller {
    //Render ra trang dashboard
    @GetMapping("/dashboard")
    public String render_listparent(HttpServletRequest request, Model model) {
        return "dashboard_view";
    }

    //Render...
    @GetMapping("/subitem1")
    public String render_subitem(HttpServletRequest request, Model model) {
        return "subitem1";
    }
}
