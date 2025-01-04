package com.xrm.tickly.ticketing_app.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.xrm.tickly.ticketing_app.enums.UserRole;

class UserDTOTest {

    @Test
    void testUserDTO() {
        UserDTO userDTO = new UserDTO();
        
         
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setEmail("testuser@example.com");
        userDTO.setRole(UserRole.USER);
        
         
        assertEquals(1L, userDTO.getId());
        assertEquals("testuser", userDTO.getUsername());
        assertEquals("testuser@example.com", userDTO.getEmail());
        assertEquals(UserRole.USER, userDTO.getRole());
    }
} 