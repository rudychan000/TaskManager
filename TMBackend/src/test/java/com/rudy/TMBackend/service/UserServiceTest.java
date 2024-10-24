package com.rudy.TMBackend.service;

import com.rudy.TMBackend.model.*;
import com.rudy.TMBackend.repository.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private User user1;
    private User user2;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        user1 = new User("Alice", "user1email", "password1");
        user2 = new User("Bob", "user2email", "password2");
    }

    @Test
    public void testSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user1);
        User savedUser = userService.saveUser(user1);
        assertEquals(user1, savedUser);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);
        List<User> allUsers = userService.getAllUsers();
        assertEquals(users, allUsers);
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        User user = userService.getUserById(1L);
        assertEquals(user1, user);
    }

    @Test
    public void testGetUserByName() {
        when(userRepository.findByName("Alice")).thenReturn(Optional.of(user1));
        User user = userService.getUserByName("Alice");
        assertEquals(user1, user);
    }

    @Test
    public void testGetUserByEmail() {
        when(userRepository.findByEmail("user1email")).thenReturn(Optional.of(user1));
        User user = userService.getUserByEmail("user1email");
        assertEquals(user1, user);
    }

    @Test
    public void testGetUserByEmailNotFound() {
        when(userRepository.findByEmail("user1email")).thenReturn(Optional.empty());
        User user = userService.getUserByEmail("user1email");
        assertNull(user);
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User user = userService.getUserById(1L);
        assertNull(user);
    }

    @Test
    public void testGetUserByNameNotFound() {
        when(userRepository.findByName("Alice")).thenReturn(Optional.empty());
        User user = userService.getUserByName("Alice");
        assertNull(user);
    }

    @Test
    public void testSaveUserNull() {
        when(userRepository.save(null)).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(null);
        });
    }

    @Test
    public void testGetAllUsersEmpty() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        List<User> allUsers = userService.getAllUsers();
        assertEquals(new ArrayList<>(), allUsers);
    }

    @Test
    public void testGetUserByEmailEmpty() {
        when(userRepository.findByEmail("user1email")).thenReturn(Optional.empty());
        User user = userService.getUserByEmail("user1email");
        assertNull(user);
    }

    @Test
    public void testGetUserByIdEmpty() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User user = userService.getUserById(1L);
        assertNull(user);
    }

    @Test
    public void testGetUserByNameEmpty() {
        when(userRepository.findByName("Alice")).thenReturn(Optional.empty());
        User user = userService.getUserByName("Alice");
        assertNull(user);
    }


}
