package org.example.smartmanageschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.smartmanageschool.Repository.student_Repo;
import org.example.smartmanageschool.model.student_model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class homepage_controller {
    @Autowired
    student_Repo studentRepo;

    // Render trang homepage
    @GetMapping("/homepage")
    public String show_homepage(HttpServletRequest request, Model model, @RequestParam(defaultValue = "0") int page) {
        // Lay username tu session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Lay so luong phan tu tren moi trang
        int pageSize = 2;

        // Lay danh sach hoc sinh tu mongo
        List<student_model> studentModels = studentRepo.findByUsername(username);

        // Phan trang
        int totalPages = (int) Math.ceil((double) studentModels.size() / pageSize);

        // Gioi han trang hien tai trong khoang tu 0 den tong so trang -1
        page = Math.min(Math.max(0, page), totalPages - 1);

        // Lay sublist cho trang hien tai
        int start = page * pageSize;
        int end = Math.min(start + pageSize, studentModels.size());
        List<student_model> homepageStudents = studentModels.subList(start, end);

        //Truyen danh sach va session xuong view
        model.addAttribute("listStudent", homepageStudents);
        model.addAttribute("s_username", username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "homepage_view";
    }

    // Handle chuc nang tim kiem homepage
    @PostMapping("/homepage")
    public String search_homepage(Model model, @RequestParam("searchHomepage") String searchname, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        // Lay username tu session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Lay so luong phan tu tren moi trang
        int pageSize = 2;

        // Lay danh sach sinh vien tu MongoDB
        List<student_model> studentModels = studentRepo.findByFullnameContainingIgnoreCaseAndUsername(searchname, username);

        // Phan trang
        int totalPages = studentModels.isEmpty() ? 1 : (int) Math.ceil((double) studentModels.size() / pageSize);

        // Giới hạn trang hiện tại trong khoảng từ 0 đến tổng số trang - 1
        page = Math.min(Math.max(0, page), totalPages - 1);

        // Lấy sublist cho trang hiện tại
        int start = page * pageSize;
        int end = Math.min(start + pageSize, studentModels.size());
        List<student_model> homepageStudents = studentModels.subList(start, end);

        model.addAttribute("listStudent", homepageStudents);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "homepage_view";
    }

    // Handle chuc nang filter homepage
    @PostMapping("/homepage_filter")
    public String homepage_filter(@RequestParam("filter_grade_homepage") String filter_grade_homepage, @RequestParam("filter_homeroomteacher_homepage") String filter_homeroomteacher_homepage, Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        // Lấy username từ session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Số lượng phần tử trên mỗi trang
        int pageSize = 2;

        // Lấy danh sách sinh viên từ MongoDB
        List<student_model> studentModels = studentRepo.findByGradeAndHomeroomteacherAndUsername(filter_grade_homepage, filter_homeroomteacher_homepage, username);

        // Phân trang
        int totalPages = studentModels.isEmpty() ? 1 : (int) Math.ceil((double) studentModels.size() / pageSize);

        // Giới hạn trang hiện tại trong khoảng từ 0 đến tổng số trang - 1
        page = Math.min(Math.max(0, page), totalPages - 1);

        // Lấy sublist cho trang hiện tại
        int start = page * pageSize;
        int end = Math.min(start + pageSize, studentModels.size());
        List<student_model> homepageStudents = studentModels.subList(start, end);

        // Truyền danh sách và session xuống view
        model.addAttribute("listStudent", homepageStudents);
        model.addAttribute("s_username", username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "homepage_view";
    }
}

