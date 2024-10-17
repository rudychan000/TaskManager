package com.rudy.TMBackend.service;

import com.rudy.TMBackend.model.User;
import com.rudy.TMBackend.repository.UserRepository;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
