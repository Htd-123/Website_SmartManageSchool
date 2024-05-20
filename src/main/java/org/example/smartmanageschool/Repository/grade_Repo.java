package org.example.smartmanageschool.Repository;

import org.example.smartmanageschool.model.grade_model;
import org.example.smartmanageschool.model.student_model;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface grade_Repo extends MongoRepository<grade_model, String> {
    List<grade_model> findByUsername(String username);
    List<grade_model> findByHomeroomteacherContainingIgnoreCaseAndUsername(String homeroomteacher, String username);
    List<grade_model> findByGradeAndNumberofclassAndUsername(String grade, Double numberofclass,String username);
}
