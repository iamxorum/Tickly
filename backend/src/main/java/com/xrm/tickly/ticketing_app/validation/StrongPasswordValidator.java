package com.xrm.tickly.ticketing_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    @Override
    public void initialize(StrongPassword constraintAnnotation) {
         
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        
        return password.length() >= 8 &&  
               password.matches(".*[A-Z].*") &&  
               password.matches(".*[a-z].*") &&  
               password.matches(".*\\d.*") &&  
               password.matches(".*[!@#$%^&*()\\-_=+{};:,<.>].*");  
    }
} 