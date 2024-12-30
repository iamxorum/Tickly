package com.xrm.tickly.ticketing_app.controller;

import com.xrm.tickly.ticketing_app.dto.FileAttachmentDTO;
import com.xrm.tickly.ticketing_app.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/tickets/{ticketId}/files")
@RequiredArgsConstructor
@Tag(name = "File Management", description = "APIs for managing ticket attachments")
public class FileController {
    private final FileService fileService;

    @PostMapping
    @Operation(
        summary = "Upload file",
        description = "Uploads a file attachment to a ticket"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid file"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to upload files")
    })
    public ResponseEntity<FileAttachmentDTO> uploadFile(
            @Parameter(description = "ID of the ticket") 
            @PathVariable Long ticketId,
            @Parameter(description = "File to upload") 
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileService.storeFile(ticketId, file));
    }

    @GetMapping("/{fileId}")
    @Operation(
        summary = "Download file",
        description = "Downloads a specific file attachment"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "File downloaded successfully"),
        @ApiResponse(responseCode = "404", description = "File not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to download this file")
    })
    public ResponseEntity<Resource> downloadFile(
            @Parameter(description = "ID of the ticket") 
            @PathVariable Long ticketId,
            @Parameter(description = "ID of the file to download") 
            @PathVariable Long fileId) {
        Resource resource = fileService.loadFileAsResource(fileId);
        String contentType = "application/octet-stream";
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{fileId}")
    @Operation(
        summary = "Delete file",
        description = "Deletes a file attachment from a ticket"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "File deleted successfully"),
        @ApiResponse(responseCode = "404", description = "File not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to delete this file")
    })
    public ResponseEntity<Void> deleteFile(
            @Parameter(description = "ID of the ticket") 
            @PathVariable Long ticketId,
            @Parameter(description = "ID of the file to delete") 
            @PathVariable Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }
}