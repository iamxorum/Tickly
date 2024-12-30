package com.xrm.tickly.ticketing_app.dto;

import com.xrm.tickly.ticketing_app.validation.ValidationGroups;
import com.xrm.tickly.ticketing_app.validation.ValidPriority;
import com.xrm.tickly.ticketing_app.validation.ValidTicketDates;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import com.xrm.tickly.ticketing_app.enums.TicketStatus;
import com.xrm.tickly.ticketing_app.enums.TicketPriority;

@Data
@ValidTicketDates(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
public class TicketDTO {
    @Null(groups = ValidationGroups.Create.class)
    @NotNull(groups = ValidationGroups.Update.class)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100)
    private String title;

    @Size(max = 2000)
    private String description;

    @NotNull(message = "Priority is required")
    @ValidPriority
    private TicketPriority priority;

    @NotNull(message = "Status is required")
    private TicketStatus status;

    @Future(message = "Due date must be in the future")
    private LocalDateTime dueDate;

    private LocalDateTime startDate;

    @NotNull(message = "Assignee ID is required")
    private Long assigneeId;

    @NotNull(message = "Reporter ID is required")
    private Long reporterId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}