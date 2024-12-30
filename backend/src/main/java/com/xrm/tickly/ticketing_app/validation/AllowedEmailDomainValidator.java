package com.xrm.tickly.ticketing_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedEmailDomainValidator implements ConstraintValidator<AllowedEmailDomain, String> {
    private String[] allowedDomains;

    @Override
    public void initialize(AllowedEmailDomain constraintAnnotation) {
        this.allowedDomains = constraintAnnotation.allowedDomains();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true; // Let @NotNull or @NotBlank handle this
        }

        String domain = email.substring(email.indexOf("@") + 1).toLowerCase();
        for (String allowedDomain : allowedDomains) {
            if (domain.equals(allowedDomain.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}