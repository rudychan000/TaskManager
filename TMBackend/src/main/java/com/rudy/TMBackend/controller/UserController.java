package com.rudy.TMBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rudy.TMBackend.model.User;
import com.rudy.TMBackend.service.UserService;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    //create a new user
    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.saveUser(user);
    }

    //get all users
    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    //get user by email
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    //get user by name
    @GetMapping("/name/{name}")
    public User getUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }

    //get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

}
