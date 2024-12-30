package com.xrm.tickly.ticketing_app.controller;

import com.xrm.tickly.ticketing_app.dto.ProjectDTO;
import com.xrm.tickly.ticketing_app.service.ProjectService;
import com.xrm.tickly.ticketing_app.validation.ValidationGroups;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(name = "Project Management", description = "APIs for managing projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    @Operation(
        summary = "Get all projects",
        description = "Retrieves all projects with optional pagination"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved projects"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view projects")
    })
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get project by ID",
        description = "Retrieves a specific project by its ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Project found successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view this project")
    })
    public ResponseEntity<ProjectDTO> getProjectById(
            @Parameter(description = "ID of the project to retrieve") 
            @PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PostMapping
    @Operation(
        summary = "Create new project",
        description = "Creates a new project in the system"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Project created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid project data"),
        @ApiResponse(responseCode = "403", description = "Not authorized to create projects")
    })
    public ResponseEntity<ProjectDTO> createProject(
            @Parameter(description = "Project details") 
            @Valid @RequestBody ProjectDTO projectDTO) {
        return new ResponseEntity<>(projectService.createProject(projectDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update project",
        description = "Updates an existing project's information"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Project updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid project data"),
        @ApiResponse(responseCode = "404", description = "Project not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to update this project")
    })
    public ResponseEntity<ProjectDTO> updateProject(
            @Parameter(description = "ID of the project to update") 
            @PathVariable Long id,
            @Parameter(description = "Updated project details") 
            @Valid @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete project",
        description = "Deletes an existing project"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Project not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to delete this project")
    })
    public ResponseEntity<Void> deleteProject(
            @Parameter(description = "ID of the project to delete") 
            @PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/members/{userId}")
    @Operation(
        summary = "Add member to project",
        description = "Adds a user to a project's member list"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Member added successfully"),
        @ApiResponse(responseCode = "404", description = "Project or user not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to modify project members")
    })
    public ResponseEntity<ProjectDTO> addMemberToProject(
            @Parameter(description = "ID of the project") 
            @PathVariable Long id,
            @Parameter(description = "ID of the user to add") 
            @PathVariable Long userId) {
        return ResponseEntity.ok(projectService.addMemberToProject(id, userId));
    }
}