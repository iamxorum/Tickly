package com.xrm.tickly.ticketing_app.service;

import com.xrm.tickly.ticketing_app.dto.*;
import com.xrm.tickly.ticketing_app.repository.*;
import com.xrm.tickly.ticketing_app.enums.TicketPriority;
import com.xrm.tickly.ticketing_app.enums.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final AsyncValidationService asyncValidationService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TicketRepository ticketRepository;

    public CompletableFuture<ValidationResult> validateTicket(TicketDTO ticket) {
        Map<String, String> errors = new HashMap<>();

        return CompletableFuture.supplyAsync(() -> {
            // Required fields validation
            if (ticket.getTitle() == null || ticket.getTitle().trim().isEmpty()) {
                errors.put("title", "Title is required");
            }

            // Status validation
            if (!isValidStatus(ticket.getStatus())) {
                errors.put("status", "Invalid status");
            }

            // Priority validation
            if (!isValidPriority(ticket.getPriority())) {
                errors.put("priority", "Invalid priority level");
            }

            // Date validation
            if (ticket.getDueDate() != null && ticket.getStartDate() != null 
                && ticket.getDueDate().isBefore(ticket.getStartDate())) {
                errors.put("dueDate", "Due date must be after start date");
            }

            // Assignee validation
            if (ticket.getAssigneeId() != null && !userRepository.existsById(ticket.getAssigneeId())) {
                errors.put("assigneeId", "Invalid assignee");
            }

            // Reporter validation
            if (ticket.getReporterId() != null && !userRepository.existsById(ticket.getReporterId())) {
                errors.put("reporterId", "Invalid reporter");
            }

            return ValidationResult.builder()
                    .valid(errors.isEmpty())
                    .errors(errors)
                    .build();
        });
    }

    public CompletableFuture<ValidationResult> validateProject(ProjectDTO project) {
        Map<String, String> errors = new HashMap<>();

        return CompletableFuture.supplyAsync(() -> {
            // Name validation
            if (project.getName() == null || project.getName().trim().isEmpty()) {
                errors.put("name", "Project name is required");
            } else if (projectRepository.existsByName(project.getName())) {
                errors.put("name", "Project name already exists");
            }

            // Status validation
            if (project.getStatus() == null) {
                errors.put("status", "Project status is required");
            }

            // Members validation
            if (project.getMemberIds() == null || project.getMemberIds().isEmpty()) {
                errors.put("members", "Project must have at least one member");
            } else {
                // Validate all member IDs exist
                for (Long memberId : project.getMemberIds()) {
                    if (!userRepository.existsById(memberId)) {
                        errors.put("members", "Invalid member ID: " + memberId);
                        break;
                    }
                }
            }

            // Date validation
            if (project.getEndDate() != null && project.getStartDate() != null 
                && project.getEndDate().isBefore(project.getStartDate())) {
                errors.put("endDate", "End date must be after start date");
            }

            return ValidationResult.builder()
                    .valid(errors.isEmpty())
                    .errors(errors)
                    .build();
        });
    }

    public CompletableFuture<ValidationResult> validateUserRegistration(RegisterRequestDTO request) {
        Map<String, String> errors = new HashMap<>();

        return CompletableFuture.supplyAsync(() -> {
            // Username validation
            if (userRepository.existsByUsername(request.getUsername())) {
                errors.put("username", "Username already exists");
            }

            // Email validation
            if (userRepository.existsByEmail(request.getEmail())) {
                errors.put("email", "Email already exists");
            }

            // Password strength validation
            if (!isPasswordStrong(request.getPassword())) {
                errors.put("password", "Password does not meet strength requirements");
            }

            return ValidationResult.builder()
                .valid(errors.isEmpty())
                .errors(errors)
                .build();
        });
    }

    public CompletableFuture<ValidationResult> validateUser(RegisterRequestDTO request) {
        Map<String, String> errors = new HashMap<>();

        return CompletableFuture.supplyAsync(() -> {
            // Username validation
            if (userRepository.existsByUsername(request.getUsername())) {
                errors.put("username", "Username already exists");
            }

            // Email validation
            if (userRepository.existsByEmail(request.getEmail())) {
                errors.put("email", "Email already exists");
            }

            // Password strength validation
            if (!isPasswordStrong(request.getPassword())) {
                errors.put("password", "Password does not meet strength requirements");
            }

            return ValidationResult.builder()
                .valid(errors.isEmpty())
                .errors(errors)
                .build();
        });
    }

    public CompletableFuture<ValidationResult> validateComment(CommentDTO comment) {
        Map<String, String> errors = new HashMap<>();

        return CompletableFuture.supplyAsync(() -> {
            // Content validation
            if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
                errors.put("content", "Comment content is required");
            }

            // Ticket validation
            if (comment.getTicketId() != null && !ticketRepository.existsById(comment.getTicketId())) {
                errors.put("ticketId", "Invalid ticket ID");
            }

            return ValidationResult.builder()
                .valid(errors.isEmpty())
                .errors(errors)
                .build();
        });
    }

    public CompletableFuture<ValidationResult> validateEmail(String email) {
        Map<String, String> errors = new HashMap<>();

        return CompletableFuture.supplyAsync(() -> {
            if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                errors.put("email", "Invalid email format");
            } else if (userRepository.existsByEmail(email)) {
                errors.put("email", "Email already exists");
            }

            return ValidationResult.builder()
                .valid(errors.isEmpty())
                .errors(errors)
                .build();
        });
    }

    public CompletableFuture<ValidationResult> validateUsername(String username) {
        Map<String, String> errors = new HashMap<>();

        return CompletableFuture.supplyAsync(() -> {
            if (username == null || !username.matches("^[a-zA-Z0-9._-]{3,50}$")) {
                errors.put("username", "Invalid username format");
            } else if (userRepository.existsByUsername(username)) {
                errors.put("username", "Username already exists");
            }

            return ValidationResult.builder()
                .valid(errors.isEmpty())
                .errors(errors)
                .build();
        });
    }

    private boolean isValidStatus(TicketStatus status) {
        return status != null && Arrays.asList(TicketStatus.values()).contains(status);
    }

    private boolean isValidPriority(TicketPriority priority) {
        return priority != null && Arrays.asList(TicketPriority.values()).contains(priority);
    }

    private boolean isPasswordStrong(String password) {
        if (password == null) return false;
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&  // at least one uppercase
               password.matches(".*[a-z].*") &&  // at least one lowercase
               password.matches(".*\\d.*") &&    // at least one digit
               password.matches(".*[!@#$%^&*()\\-_=+{};:,<.>].*"); // at least one special char
    }
}