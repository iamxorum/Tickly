package com.xrm.tickly.ticketing_app.service;

import com.xrm.tickly.ticketing_app.repository.UserRepository;
import com.xrm.tickly.ticketing_app.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AsyncValidationService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Async("validationExecutor")
    public CompletableFuture<Boolean> isEmailAvailable(String email) {
        return CompletableFuture.completedFuture(!userRepository.existsByEmail(email));
    }

    @Async("validationExecutor")
    public CompletableFuture<Boolean> isUsernameAvailable(String username) {
        return CompletableFuture.completedFuture(!userRepository.existsByUsername(username));
    }

    @Async("validationExecutor")
    public CompletableFuture<Boolean> isProjectNameAvailable(String projectName) {
        return CompletableFuture.completedFuture(!projectRepository.existsByName(projectName));
    }

    @Async("validationExecutor")
    public CompletableFuture<Boolean> validateUserExists(Long userId) {
        return CompletableFuture.completedFuture(userRepository.existsById(userId));
    }

    @Async("validationExecutor")
    public CompletableFuture<Boolean> validateProjectExists(Long projectId) {
        return CompletableFuture.completedFuture(projectRepository.existsById(projectId));
    }
}