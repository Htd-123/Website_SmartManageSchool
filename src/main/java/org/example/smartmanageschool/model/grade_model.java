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
public class grade_model {
    @Id
    private String id;
    private String username;
    private String grade;
    private int numberofclass;
    private String homeroomteacher;
    private String classmonitor;
    private String phoneclassmonitor;
}
