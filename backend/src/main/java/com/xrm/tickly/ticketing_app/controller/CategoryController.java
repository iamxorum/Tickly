package com.xrm.tickly.ticketing_app.controller;

import com.xrm.tickly.ticketing_app.dto.CategoryDTO;
import com.xrm.tickly.ticketing_app.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "Category management APIs")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
        summary = "Get all categories",
        description = "Retrieves a list of all available categories"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved all categories",
        content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = CategoryDTO.class))
    )
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(
        summary = "Get category by ID",
        description = "Retrieves a specific category by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Category found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CategoryDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Category not found",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(
            @Parameter(description = "ID of the category to retrieve") 
            @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(
        summary = "Create a new category",
        description = "Creates a new category with the provided information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Category created successfully",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CategoryDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Category with the same name already exists",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(
            @Parameter(
                description = "Category details", 
                required = true
            )
            @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update an existing category",
        description = "Updates a category's information by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Category updated successfully",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CategoryDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Category not found",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Category with the same name already exists",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @Parameter(description = "ID of the category to update") 
            @PathVariable Long id,
            @Parameter(
                description = "Updated category details", 
                required = true
            )
            @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @Operation(
        summary = "Delete a category",
        description = "Deletes a category by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Category deleted successfully",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Category not found",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID of the category to delete") 
            @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}