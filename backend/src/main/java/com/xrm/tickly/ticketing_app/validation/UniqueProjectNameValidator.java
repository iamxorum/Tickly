package com.xrm.tickly.ticketing_app.validation;

import com.xrm.tickly.ticketing_app.repository.ProjectRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueProjectNameValidator implements ConstraintValidator<UniqueProjectName, String> {
    private final ProjectRepository projectRepository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name != null && !projectRepository.existsByName(name);
    }
}