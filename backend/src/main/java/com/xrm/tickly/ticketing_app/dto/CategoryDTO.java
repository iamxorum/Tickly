package com.xrm.tickly.ticketing_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CategoryDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotBlank(message = "Category name is required")
    private String name;
    
    private String description;
    
    @Pattern(
        regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$",
        message = "Color code must be a valid hex color (e.g., #FF0000 or #F00)"
    )
    private String colorCode;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}