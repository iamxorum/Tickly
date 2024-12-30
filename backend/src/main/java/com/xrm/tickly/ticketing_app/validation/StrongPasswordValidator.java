package com.xrm.tickly.ticketing_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    @Override
    public void initialize(StrongPassword constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        
        return password.length() >= 8 && // at least 8 chars
               password.matches(".*[A-Z].*") && // at least one uppercase
               password.matches(".*[a-z].*") && // at least one lowercase
               password.matches(".*\\d.*") && // at least one digit
               password.matches(".*[!@#$%^&*()\\-_=+{};:,<.>].*"); // at least one special char
    }
} 