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

    public CommentDTO() {}

    public CommentDTO(Long id, String content, Long userId, String username, Long ticketId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.ticketId = ticketId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
