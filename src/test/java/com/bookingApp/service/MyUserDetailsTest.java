package com.bookingApp.service;

import com.bookingApp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyUserDetailsTest {

    private MyUserDetails myUserDetails;
    private User user;

    @BeforeEach
    public void setUp() {
        // create mock user
        user = Mockito.mock(User.class);

        // mock behavior 4user
        Mockito.when(user.getUsername()).thenReturn("testUser");
        Mockito.when(user.getPassword()).thenReturn("testPassword");
        Mockito.when(user.getRole()).thenReturn("ROLE_USER");

        // myUserDetails with mocked user
        myUserDetails = new MyUserDetails(user);
    }

    @Test
    public void testGetUsername() {
        String username = myUserDetails.getUsername();

        assertEquals("testUser", username, "The username should match.");
    }

    @Test
    public void testGetPassword() {
        String password = myUserDetails.getPassword();

        assertEquals("testPassword", password, "The password should match.");
    }

    @Test
    public void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = myUserDetails.getAuthorities();

        assertEquals(1, authorities.size(), "There should be one authority.");
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")), "The authority should be ROLE_USER.");
    }

    @Test
    public void testIsAccountNonExpired() {
        boolean isNonExpired = myUserDetails.isAccountNonExpired();

        assertTrue(isNonExpired, "The account should be non-expired.");
    }

    @Test
    public void testIsAccountNonLocked() {
        boolean isNonLocked = myUserDetails.isAccountNonLocked();

        assertTrue(isNonLocked, "The account should be non-locked.");
    }

    @Test
    public void testIsCredentialsNonExpired() {
        boolean isNonExpired = myUserDetails.isCredentialsNonExpired();

        assertTrue(isNonExpired, "The credentials should be non-expired.");
    }

    @Test
    public void testIsEnabled() {
        boolean isEnabled = myUserDetails.isEnabled();

        assertTrue(isEnabled, "The account should be enabled.");
    }
}

