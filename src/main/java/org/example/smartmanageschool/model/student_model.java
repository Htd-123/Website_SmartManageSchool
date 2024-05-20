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
public class student_model {
    @Id
    private String id;
    private String fullname;
    private String homeroomteacher;
    private String grade;
    private String username;
    private String phoneparent;
    private Double math;
    private Double physical;
    private Double chemistry;
    private Double literature;
    private Double english;
    private String classify;
}
