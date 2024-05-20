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
public class parent_model {
    @Id
    private String id;
    private String fullname;
    private String grade;
    private String fathername;
    private String mothername;
    private String phonefather;
    private String phonemother;
    private String address;
    private String username;
}
