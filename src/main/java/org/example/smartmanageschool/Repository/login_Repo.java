package org.example.smartmanageschool.Repository;

import org.example.smartmanageschool.model.login_model;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface login_Repo extends MongoRepository<login_model, String> {
    login_model findByUsername(String username);
}
