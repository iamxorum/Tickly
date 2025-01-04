package com.xrm.tickly.ticketing_app.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthRequestDTOTest {

    @Test
    void testAuthRequestDTO() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        
         
        authRequestDTO.setUsername("testuser");
        authRequestDTO.setPassword("password123");
        
         
        assertEquals("testuser", authRequestDTO.getUsername());
        assertEquals("password123", authRequestDTO.getPassword());
    }
} 