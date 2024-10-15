package com.rudy.TMBackend.service;

import com.rudy.TMBackend.model.Test;
import com.rudy.TMBackend.repository.TestRepository;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // This means that this class is a Service
public class TestService {
    
    @Autowired
    private TestRepository testRepository; // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data

    public Test saveTest(Test test) {
        return testRepository.save(test);
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Test getTestById(int id) {
        return testRepository.findById(id).orElse(null);
    }

    public Test getTestByName(String name) {
        return testRepository.findByName(name);
    }
}