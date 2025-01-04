package com.xrm.tickly.ticketing_app.controller;

import com.xrm.tickly.ticketing_app.dto.CategoryDTO;
import com.xrm.tickly.ticketing_app.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category Management", description = "APIs for managing categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieves all categories in the system")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved categories"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view categories")
    })
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieves a specific category by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Category found successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view this category")
    })
    public ResponseEntity<CategoryDTO> getCategoryById(
            @Parameter(description = "ID of the category to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    @Operation(summary = "Create new category", description = "Creates a new category in the system")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Category created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid category data"),
        @ApiResponse(responseCode = "403", description = "Not authorized to create categories")
    })
    public ResponseEntity<CategoryDTO> createCategory(
            @Parameter(description = "Category details") @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(201).body(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Updates an existing category's information")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Category updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid category data"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to update this category")
    })
    public ResponseEntity<CategoryDTO> updateCategory(
            @Parameter(description = "ID of the category to update") @PathVariable Long id,
            @Parameter(description = "Updated category details") @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setId(id); 
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Deletes an existing category")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to delete this category")
    })
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID of the category to delete") @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}