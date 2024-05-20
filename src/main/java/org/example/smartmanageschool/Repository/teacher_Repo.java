package org.example.smartmanageschool.Repository;

import org.example.smartmanageschool.model.teacher_model;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface teacher_Repo extends MongoRepository<teacher_model, String> {
}
