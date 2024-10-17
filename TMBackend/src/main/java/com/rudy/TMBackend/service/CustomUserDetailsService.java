package com.rudy.TMBackend.service;

import com.rudy.TMBackend.model.User;
import com.rudy.TMBackend.repository.UserRepository;
import com.rudy.TMBackend.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // Load user by username or email
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Fetch user from the database
        User user = userRepository.findByNameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        return UserPrincipal.create(user);
    }

    // Load user by ID
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() ->
                new UsernameNotFoundException("User not found with id: " + id));

        return UserPrincipal.create(user);
    }
}
