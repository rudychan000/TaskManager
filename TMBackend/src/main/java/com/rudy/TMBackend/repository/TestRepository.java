package com.rudy.TMBackend.repository;

import com.rudy.TMBackend.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TestRepository extends JpaRepository<Test, Integer> {
    Test findByName(String name);
}
