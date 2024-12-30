package com.xrm.tickly.ticketing_app.controller;

import com.xrm.tickly.ticketing_app.dto.*;
import com.xrm.tickly.ticketing_app.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/validate")
@RequiredArgsConstructor
@Tag(name = "Data Validation", description = "APIs for validating various entities before creation/update")
public class ValidationController {
    private final ValidationService validationService;

    @PostMapping("/user")
    @Operation(
        summary = "Validate user data",
        description = "Validates user registration data before actual registration"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Validation completed",
            content = @Content(schema = @Schema(implementation = ValidationResult.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request format"
        )
    })
    public CompletableFuture<ResponseEntity<ValidationResult>> validateUser(
            @Parameter(description = "User data to validate") 
            @RequestBody RegisterRequestDTO user) {
        return validationService.validateUser(user)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/project")
    @Operation(
        summary = "Validate project data",
        description = "Validates project data before creation/update"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Validation completed",
            content = @Content(schema = @Schema(implementation = ValidationResult.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request format"
        )
    })
    public CompletableFuture<ResponseEntity<ValidationResult>> validateProject(
            @Parameter(description = "Project data to validate") 
            @RequestBody ProjectDTO project) {
        return validationService.validateProject(project)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/ticket")
    @Operation(
        summary = "Validate ticket data",
        description = "Validates ticket data before creation/update"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Validation completed",
            content = @Content(schema = @Schema(implementation = ValidationResult.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request format"
        )
    })
    public CompletableFuture<ResponseEntity<ValidationResult>> validateTicket(
            @Parameter(description = "Ticket data to validate") 
            @RequestBody TicketDTO ticket) {
        return validationService.validateTicket(ticket)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/comment")
    @Operation(
        summary = "Validate comment data",
        description = "Validates comment data before creation/update"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Validation completed",
            content = @Content(schema = @Schema(implementation = ValidationResult.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request format"
        )
    })
    public CompletableFuture<ResponseEntity<ValidationResult>> validateComment(
            @Parameter(description = "Comment data to validate") 
            @RequestBody CommentDTO comment) {
        return validationService.validateComment(comment)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/email")
    @Operation(
        summary = "Validate email address",
        description = "Validates an email address format and availability"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Validation completed",
            content = @Content(schema = @Schema(implementation = ValidationResult.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request format"
        )
    })
    public CompletableFuture<ResponseEntity<ValidationResult>> validateEmail(
            @Parameter(description = "Email address to validate") 
            @RequestParam String email) {
        return validationService.validateEmail(email)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/username")
    @Operation(
        summary = "Validate username",
        description = "Validates username format and availability"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", 
            description = "Validation completed",
            content = @Content(schema = @Schema(implementation = ValidationResult.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request format"
        )
    })
    public CompletableFuture<ResponseEntity<ValidationResult>> validateUsername(
            @Parameter(description = "Username to validate") 
            @RequestParam String username) {
        return validationService.validateUsername(username)
                .thenApply(ResponseEntity::ok);
    }
}