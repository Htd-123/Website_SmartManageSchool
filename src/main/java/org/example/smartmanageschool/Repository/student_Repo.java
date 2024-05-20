package org.example.smartmanageschool.Repository;

import org.example.smartmanageschool.model.student_model;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface student_Repo extends MongoRepository<student_model, String> {
    List<student_model> findByUsername(String username);
    List<student_model> findByFullnameContainingIgnoreCaseAndUsername(String fullname, String username);
    List<student_model> findByGradeAndClassifyAndUsername(String grade, String classify,String username);
    List<student_model> findByGradeAndHomeroomteacherAndUsername(String grade, String homeroomteacher,String username);
}