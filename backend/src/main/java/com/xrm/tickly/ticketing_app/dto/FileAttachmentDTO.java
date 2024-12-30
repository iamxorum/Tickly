package com.xrm.tickly.ticketing_app.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FileAttachmentDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private Long size;
    private Long ticketId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 