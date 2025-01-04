package com.xrm.tickly.ticketing_app.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.xrm.tickly.ticketing_app.enums.UserRole;

class UserTest {

    @Test
    void testUser() {
        User user = new User();
        
         
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setRole(UserRole.USER);
        
         
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("testuser@example.com", user.getEmail());
        assertEquals(UserRole.USER, user.getRole());
    }
}