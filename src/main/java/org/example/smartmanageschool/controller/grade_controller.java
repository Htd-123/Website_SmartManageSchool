package org.example.smartmanageschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.smartmanageschool.Repository.grade_Repo;
import org.example.smartmanageschool.model.grade_model;
import org.example.smartmanageschool.model.student_model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class grade_controller {
    @Autowired
    grade_Repo gradeRepo;

    // Render trang lop hoc
    @GetMapping("/grade")
    public String render_grade(HttpServletRequest request, Model model, @RequestParam(defaultValue = "0") int page) {
        // Lay username tu session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Số lượng phần tử trên mỗi trang
        int pageSize = 2;

        // Lay danh sach lop hoc tu mongo
        List<grade_model> gradeModels = gradeRepo.findByUsername(username);

        // Phân trang
        int totalPages = (int) Math.ceil((double) gradeModels.size() / pageSize);

        // Giới hạn trang hiện tại trong khoảng từ 0 đến tổng số trang - 1
        page = Math.min(Math.max(0, page), totalPages - 1);

        // Lấy sublist cho trang hiện tại
        int start = page * pageSize;
        int end = Math.min(start + pageSize, gradeModels.size());
        List<grade_model> gradeStudents = gradeModels.subList(start, end);

        // Truyen danh sach lop hoc va session xuong view
        model.addAttribute("listGrade", gradeStudents);
        model.addAttribute("s_username", username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "grade_view";
    }

    // Handle chuc nang tim kiem
    @PostMapping("/grade")
    public String grade_search(Model model, HttpServletRequest request, @RequestParam("searchGVCN") String searchGVCN, @RequestParam(defaultValue = "0") int page) {
        //Lay username tu session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Số lượng phần tử trên mỗi trang
        int pageSize = 2;

        List<grade_model> gradeModels = gradeRepo.findByHomeroomteacherContainingIgnoreCaseAndUsername(searchGVCN, username);

        // Phân trang
        int totalPages = gradeModels.isEmpty() ? 1 : (int) Math.ceil((double) gradeModels.size() / pageSize);

        // Giới hạn trang hiện tại trong khoảng từ 0 đến tổng số trang - 1
        page = Math.min(Math.max(0, page), totalPages - 1);

        // Lấy sublist cho trang hiện tại
        int start = page * pageSize;
        int end = Math.min(start + pageSize, gradeModels.size());
        List<grade_model> pageGrade = gradeModels.subList(start, end);

        model.addAttribute("listGrade", pageGrade);
        model.addAttribute("s_username", username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "grade_view";
    }

    // Handle chuc nang filter
    @PostMapping("/grade_filter")
    public String grade_filter(@RequestParam("filter_grade_pagegrade") String filter_grade_pagegrade,  @RequestParam("filter_sizegrade_pagegrade") Double filter_sizegrade_pagegrade ,Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        // Lấy username từ session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Số lượng phần tử trên mỗi trang
        int pageSize = 2;

        // Lấy danh sách sinh viên từ MongoDB
        List<grade_model> gradeModels = gradeRepo.findByGradeAndNumberofclassAndUsername(filter_grade_pagegrade, filter_sizegrade_pagegrade, username);

        // Phân trang
        int totalPages = gradeModels.isEmpty() ? 1 : (int) Math.ceil((double) gradeModels.size() / pageSize);

        // Giới hạn trang hiện tại trong khoảng từ 0 đến tổng số trang - 1
        page = Math.min(Math.max(0, page), totalPages - 1);

        // Lấy sublist cho trang hiện tại
        int start = page * pageSize;
        int end = Math.min(start + pageSize, gradeModels.size());
        List<grade_model> pageGrade = gradeModels.subList(start, end);

        // Truyền danh sách và session xuống view
        model.addAttribute("listGrade", pageGrade);
        model.addAttribute("s_username", username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "grade_view";
    }

    // Render trang update grade
    @GetMapping("grade/{id_update}")
    public String grade_update(@PathVariable("id_update") String id, Model model) {
        grade_model gradeModel = gradeRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID" + id));
        model.addAttribute("info_grade", gradeModel);
        return "update_grade";
    }
}
