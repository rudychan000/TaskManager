package com.rudy.TMBackend.controller;

import com.rudy.TMBackend.model.Test;
import com.rudy.TMBackend.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController // This means that this class is a RestController
@RequestMapping("/api/tests") // This means URL's start with /demo (after Application path)
public class TestController {
    
    @Autowired
    private TestService testService;

    //create a new test
    @PostMapping("/create")
    public Test createTest(@RequestBody Test test) {
        return testService.saveTest(test);
    }
    
    //get all tests
    @GetMapping("/all")
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }

    //get test by id
    @GetMapping("/{id}")
    public Test getTestById(@PathVariable int id) {
        return testService.getTestById(id);
    }
}
