package com.xrm.tickly.ticketing_app_test.validation;

import com.xrm.tickly.ticketing_app.validation.StrongPasswordValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StrongPasswordValidatorTest {

    private StrongPasswordValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new StrongPasswordValidator();
    }

    @Test
    void whenPasswordValid_thenReturnTrue() {
        String validPassword = "Test123!@#";
        assertThat(validator.isValid(validPassword, context)).isTrue();
    }

    @Test
    void whenPasswordTooShort_thenReturnFalse() {
        String shortPassword = "Tt1!";
        assertThat(validator.isValid(shortPassword, context)).isFalse();
    }

    @Test
    void whenPasswordNoUpperCase_thenReturnFalse() {
        String noUpperCase = "test123!@#";
        assertThat(validator.isValid(noUpperCase, context)).isFalse();
    }

    @Test
    void whenPasswordNull_thenReturnFalse() {
        assertThat(validator.isValid(null, context)).isFalse();
    }
}