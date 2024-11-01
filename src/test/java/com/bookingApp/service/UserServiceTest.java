package com.bookingApp.service;

import com.bookingApp.model.User;
import com.bookingApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        String username = "testUser";
        String password = "password123";
        String role = "ROLE_USER";

        // create a user to save
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setPassword("encodedPassword");
        expectedUser.setRole(role);

        // return encoded password
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        // return expected user
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        User actualUser = userService.registerUser(username, password, role);

        assertEquals(expectedUser.getUsername(), actualUser.getUsername(), "Usernames should match");
        assertEquals(expectedUser.getPassword(), actualUser.getPassword(), "Passwords should match");
        assertEquals(expectedUser.getRole(), actualUser.getRole(), "Roles should match");

        // repository save
        verify(userRepository, times(1)).save(any(User.class));
        // password encoder
        verify(passwordEncoder, times(1)).encode(password);
    }
}

