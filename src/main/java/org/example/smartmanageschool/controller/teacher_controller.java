package org.example.smartmanageschool.controller;

import org.example.smartmanageschool.Repository.teacher_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class teacher_controller {
    @Autowired
    teacher_Repo teacherRepo;

    @GetMapping("teacher")
    public String render_teacher() {
        return "teacher_view";
    }
}
