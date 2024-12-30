package com.xrm.tickly.ticketing_app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueProjectNameValidator.class)
@Documented
public @interface UniqueProjectName {
    String message() default "Project name already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}