package com.xrm.tickly.ticketing_app.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthResponseDTOTest {

    @Test
    void testAuthResponseDTO() {
        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                .token("sampleToken")
                .username("testuser")
                .role("USER")
                .build();
        
         
        assertEquals("sampleToken", authResponseDTO.getToken());
        assertEquals("testuser", authResponseDTO.getUsername());
        assertEquals("USER", authResponseDTO.getRole());
    }
} 