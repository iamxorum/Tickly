package com.xrm.tickly.ticketing_app.dto;

import lombok.Data;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String content;
    private Long userId;
    private String username;
    private Long ticketId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}