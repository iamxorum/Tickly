package com.xrm.tickly.ticketing_app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TicketDatesValidator.class)
@Documented
public @interface ValidTicketDates {
    String message() default "Due date must be after start date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}