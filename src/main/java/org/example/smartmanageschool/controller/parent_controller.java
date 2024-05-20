package org.example.smartmanageschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class parent_controller {
    //Render ra danh sach phu huynh
    @GetMapping("/parent")
    public String render_listparent(HttpServletRequest request, Model model) {
        return "parent_view";
    }
}
