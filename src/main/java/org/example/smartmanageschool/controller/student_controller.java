package org.example.smartmanageschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.smartmanageschool.Repository.student_Repo;
import org.example.smartmanageschool.model.student_model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class student_controller {
    @Autowired
    student_Repo studentRepo;

    // Render ra danh sach hoc sinh
    @GetMapping("/student")
    public String render_liststudent(HttpServletRequest request, Model model,  @RequestParam(defaultValue = "0") int page) {
        // Lấy username từ session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Số lượng phần tử trên mỗi trang
        int pageSize = 2;

        // Lấy danh sách sinh viên từ mongo
        List<student_model> studentModels = studentRepo.findByUsername(username);

        // Phân trang
        int totalPages = (int) Math.ceil((double) studentModels.size() / pageSize);

        // Giới hạn trang hiện tại trong khoảng từ 0 đến tổng số trang - 1
        page = Math.min(Math.max(0, page), totalPages - 1);

        // Lấy sublist cho trang hiện tại
        int start = page * pageSize;
        int end = Math.min(start + pageSize, studentModels.size());
        List<student_model> pageStudents = studentModels.subList(start, end);

        // Truyền danh sách và session xuống view
        model.addAttribute("listStudent", pageStudents);
        model.addAttribute("s_username", username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "studentpage_view";
    }

    // Handle chuc nang tim kiem
    @PostMapping("/student")
    public String student_search(Model model, @RequestParam("searchStudent") String searchname, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        // Lấy username từ session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Số lượng phần tử trên mỗi trang
        int pageSize = 2;

        // Lấy danh sách sinh viên từ MongoDB
        List<student_model> studentModels = studentRepo.findByFullnameContainingIgnoreCaseAndUsername(searchname, username);

        // Phân trang
        int totalPages = studentModels.isEmpty() ? 1 : (int) Math.ceil((double) studentModels.size() / pageSize);

        // Giới hạn trang hiện tại trong khoảng từ 0 đến tổng số trang - 1
        page = Math.min(Math.max(0, page), totalPages - 1);

        // Lấy sublist cho trang hiện tại
        int start = page * pageSize;
        int end = Math.min(start + pageSize, studentModels.size());
        List<student_model> pageStudents = studentModels.subList(start, end);

        // Truyền danh sách và session xuống view
        model.addAttribute("listStudent", pageStudents);
        model.addAttribute("s_username", username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "studentpage_view";
    }


    // Handle xu ly xep loai hoc sinh
    public String determineClassification(Double math, Double physical, Double chemistry, Double literature, Double english) {
        double average = (math + physical + chemistry + literature + english) / 5;
        if (average >=8) {
            return "Gioi";
        } else if (average >= 6.5 && average < 8) {
            return "Kha";
        } else if (average >= 5 && average < 6.5) {
            return "Trung binh";
        } else if (average >= 3.5 && average < 5) {
            return "Yeu";
        }
        else {
            return "Kem";
        }
    }

    // Render trang update student
    @GetMapping("student/{id_update}")
    public String render_student_update(@PathVariable("id_update") String id, Model model) {
        student_model studentModel = studentRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID" + id));
        model.addAttribute("info_student", studentModel);
        return "update_student";
    }

    // Handle chuc nang update student
    @PostMapping("student/{id_update}")
    public String student_update(@PathVariable("id_update") String id, @RequestParam("fullname") String fullname, @RequestParam("grade") String grade,
                                 @RequestParam("math") Double math, @RequestParam("physical") Double physical,
                                 @RequestParam("chemistry") Double chemistry, @RequestParam("literature") Double literature, @RequestParam("english") Double english) {
        student_model studentModel = studentRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID" + id));
        String classify = determineClassification(math, physical, chemistry, literature, english);
        studentModel.setFullname(fullname);
        studentModel.setGrade(grade);
        studentModel.setMath(math);
        studentModel.setPhysical(physical);
        studentModel.setChemistry(chemistry);
        studentModel.setLiterature(literature);
        studentModel.setEnglish(english);
        studentModel.setClassify(classify);
        studentRepo.save(studentModel);
        return "redirect:/student";
    }

    // Render trang insert student
    @GetMapping("student/insert")
    public String render_student_insert() {
        return "insert_student";
    }

    // Handle chuc nang insert student
    @PostMapping("student/insert")
    public String student_insert(@RequestParam("fullname") String fullname, @RequestParam("grade") String grade,
                                 @RequestParam("gvcn") String gvcn, @RequestParam("phoneparent") String phoneparent,
                                 @RequestParam("math") Double math, @RequestParam("physical") Double physical,
                                 @RequestParam("chemistry") Double chemistry, @RequestParam("literature") Double literature,
                                 @RequestParam("english") Double english, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        student_model studentModel = new student_model();
        String classify = determineClassification(math, physical, chemistry, literature, english);
        studentModel.setFullname(fullname);
        studentModel.setGrade(grade);
        studentModel.setHomeroomteacher(gvcn);
        studentModel.setPhoneparent(phoneparent);
        studentModel.setMath(math);
        studentModel.setPhysical(physical);
        studentModel.setChemistry(chemistry);
        studentModel.setLiterature(literature);
        studentModel.setEnglish(english);
        studentModel.setClassify(classify);
        studentModel.setUsername(username);
        studentRepo.save(studentModel);
        return "redirect:/student";
    }

    // Handle chuc nang delete student
    @PostMapping("student/delete/{id_delete}")
    public String student_delete(@PathVariable("id_delete") String id) {
        studentRepo.deleteById(id);
        return "redirect:/student";
    }

    // Handle chuc nang filter student
    @PostMapping("/student_filter")
    public String student_filter(@RequestParam("filter_grade") String filter_grade, @RequestParam("filter_classify") String filter_classify, Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        // Lấy username từ session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Số lượng phần tử trên mỗi trang
        int pageSize = 2;

        // Lấy danh sách sinh viên từ MongoDB
        List<student_model> studentModels = studentRepo.findByGradeAndClassifyAndUsername(filter_grade, filter_classify, username);

        // Phân trang
        int totalPages = studentModels.isEmpty() ? 1 : (int) Math.ceil((double) studentModels.size() / pageSize);

        // Giới hạn trang hiện tại trong khoảng từ 0 đến tổng số trang - 1
        page = Math.min(Math.max(0, page), totalPages - 1);

        // Lấy sublist cho trang hiện tại
        int start = page * pageSize;
        int end = Math.min(start + pageSize, studentModels.size());
        List<student_model> pageStudents = studentModels.subList(start, end);

        // Truyền danh sách và session xuống view
        model.addAttribute("listStudent", pageStudents);
        model.addAttribute("s_username", username);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "studentpage_view";
    }
}
