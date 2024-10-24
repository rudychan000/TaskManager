package com.rudy.TMBackend.repository;

import com.rudy.TMBackend.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setName("testuser");
        user.setEmail("test@example.com");
        user = userRepository.save(user);
        
        User foundUser = userRepository.findByEmail("test@example.com").orElse(null);
        assertEquals(user, foundUser);
    }

    @Test
    public void testFindByName() {
        User user = new User();
        user.setName("testuser");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        User foundUser = userRepository.findByName("testuser").orElse(null);
        assertEquals(user, foundUser);
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setName("testuser");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        User foundUser = userRepository.findById(user.getId()).orElse(null);
        assertEquals(user, foundUser);
    }

    @Test
    public void testFindByNameOrEmail() {
        User user = new User();
        user.setName("testuser");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        User foundUser = userRepository.findByNameOrEmail("testuser", "test@example.com").orElse(null);
        assertEquals(user, foundUser);
    }

    @Test
    public void testExistsByName() {
        User user = new User();
        user.setName("testuser");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        assertTrue(userRepository.existsByName("testuser"));
    }

    @Test
    public void testExistsByEmail() {
        User user = new User();
        user.setName("testuser");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        assertTrue(userRepository.existsByEmail("test@example.com"));
    }

}
