package com.xrm.tickly.ticketing_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FileAttachmentDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    private String fileName;
    private String fileType;
    private Long size;
    private Long ticketId;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long uploadedById;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}