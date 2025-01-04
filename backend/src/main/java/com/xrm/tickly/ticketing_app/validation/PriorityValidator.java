package com.xrm.tickly.ticketing_app.validation;

import com.xrm.tickly.ticketing_app.enums.TicketPriority;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<ValidPriority, TicketPriority> {
    
    @Override
    public void initialize(ValidPriority constraintAnnotation) {
    }

    @Override
    public boolean isValid(TicketPriority priority, ConstraintValidatorContext context) {
        if (priority == null) {
            return false;
        }
        
         
        return true;
    }
}