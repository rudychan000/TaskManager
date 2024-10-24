package com.rudy.TMBackend.repository;

import com.rudy.TMBackend.model.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> findById(Long id);

    Optional<User> findByNameOrEmail(String name, String email);
    Boolean existsByName(String name);
    Boolean existsByEmail(String email);
}
