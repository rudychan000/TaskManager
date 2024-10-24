package com.rudy.TMBackend.service;

import com.rudy.TMBackend.model.*;
import com.rudy.TMBackend.repository.*;
import com.rudy.TMBackend.security.UserPrincipal;
import com.rudy.TMBackend.exception.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    private User user1;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;


    @BeforeEach
    public void setUp() {
        user1 = new User("Alice", "user1email", "password1");
    }

    @Test
    public void testLoadUserByUsername() {
        when(userRepository.findByNameOrEmail("Alice", "Alice")).thenReturn(Optional.of(user1));
        UserPrincipal userPrincipal = (UserPrincipal) customUserDetailsService.loadUserByUsername("Alice");
        assertEquals(user1.getId(), userPrincipal.getId());
    }

    @Test
    public void testLoadUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        UserPrincipal userPrincipal = (UserPrincipal) customUserDetailsService.loadUserById(1L);
        assertEquals(user1.getId(), userPrincipal.getId());
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        when(userRepository.findByNameOrEmail("Alice", "Alice")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("Alice"));
    }

    @Test
    public void testLoadUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserById(1L));
    }
}
