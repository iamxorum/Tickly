package com.xrm.tickly.ticketing_app.dto;

import com.xrm.tickly.ticketing_app.validation.UniqueProjectName;
import com.xrm.tickly.ticketing_app.validation.ValidationGroups;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;
import com.xrm.tickly.ticketing_app.enums.ProjectStatus;

@Data
public class ProjectDTO {
    @Null(groups = ValidationGroups.Create.class)
    @NotNull(groups = ValidationGroups.Update.class)
    private Long id;

    @NotBlank(message = "Project name is required")
    @Size(min = 3, max = 100)
    @UniqueProjectName(groups = ValidationGroups.Create.class)
    private String name;

    @Size(max = 2000)
    private String description;

    @NotNull(message = "Status is required")
    private ProjectStatus status;

    @NotEmpty(message = "Project must have at least one member")
    private Set<Long> memberIds;

    private LocalDateTime startDate;

    @Future(message = "End date must be in the future")
    private LocalDateTime endDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}