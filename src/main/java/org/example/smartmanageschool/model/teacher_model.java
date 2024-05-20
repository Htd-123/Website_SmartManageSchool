package org.example.smartmanageschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class teacher_model {
    @Id
    private String id;
    private String username;
    private String nameTeacher;
    private String grade;
    private String teachingSubject;
    private String phoneTeacher;
    private String gradeTeach_1;
    private String gradeTeach_2;
    private String gradeTeach_3;
    private String gradeTeach_4;
    private String gradeTeach_5;
    private String gradeTeach_6;
}
