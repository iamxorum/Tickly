package com.xrm.tickly.ticketing_app_test.validation;

import com.xrm.tickly.ticketing_app.dto.RegisterRequestDTO;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ValidationIntegrationTest {

    @Autowired
    private Validator validator;

    @Test
    void whenRegisterRequestValid_thenNoViolations() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setUsername("testuser");
        dto.setEmail("test@example.com");
        dto.setPassword("Test123!@#");

        var violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenRegisterRequestInvalid_thenViolations() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setUsername("t"); // too short
        dto.setEmail("invalid-email");
        dto.setPassword("weak");

        var violations = validator.validate(dto);
        assertThat(violations).hasSize(3);
    }
}