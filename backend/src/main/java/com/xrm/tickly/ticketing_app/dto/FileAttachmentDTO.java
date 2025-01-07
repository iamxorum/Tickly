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

    // No-argument constructor
    public FileAttachmentDTO() {}

    // All-arguments constructor (if required)
    public FileAttachmentDTO(Long id, String fileName, String fileType, Long size, Long ticketId, Long uploadedById, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.ticketId = ticketId;
        this.uploadedById = uploadedById;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
