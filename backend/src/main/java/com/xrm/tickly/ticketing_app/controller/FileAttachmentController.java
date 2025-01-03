package com.xrm.tickly.ticketing_app.controller;

import com.xrm.tickly.ticketing_app.dto.FileAttachmentDTO;
import com.xrm.tickly.ticketing_app.service.FileAttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
@Tag(name = "File Attachments", description = "File attachment management APIs")
public class FileAttachmentController {

    private final FileAttachmentService fileAttachmentService;

    @Autowired
    public FileAttachmentController(FileAttachmentService fileAttachmentService) {
        this.fileAttachmentService = fileAttachmentService;
    }

    @Operation(
        summary = "Upload a file attachment",
        description = "Upload a single file and attach it to a specific ticket"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "File uploaded successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = FileAttachmentDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input or file type",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ticket or user not found",
            content = @Content
        )
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileAttachmentDTO> uploadFile(
            @RequestParam("file")
            @Parameter(
                description = "File to upload",
                required = true,
                content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    schema = @Schema(type = "string", format = "binary")
            )) 
            MultipartFile file,

            @RequestParam("ticketId")
            @Parameter(
                description = "ID of the ticket",
                required = true,
                schema = @Schema(type = "integer", format = "int64")
            ) 
            Long ticketId,

            @RequestParam("userId")
            @Parameter(
                description = "ID of the uploading user",
                required = true,
                schema = @Schema(type = "integer", format = "int64")
            ) 
            Long userId) {
        
        FileAttachmentDTO attachment = fileAttachmentService.uploadFile(file, ticketId, userId);
        return new ResponseEntity<>(attachment, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get all attachments for a ticket",
        description = "Retrieve all file attachments associated with a specific ticket"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Files retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = FileAttachmentDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Ticket not found",
            content = @Content
        )
    })
    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<FileAttachmentDTO>> getFilesByTicketId(
            @PathVariable
            @Parameter(
                description = "ID of the ticket",
                required = true,
                schema = @Schema(type = "integer", format = "int64")
            ) 
            Long ticketId) {
        return ResponseEntity.ok(fileAttachmentService.getFilesByTicketId(ticketId));
    }

    @Operation(
        summary = "Delete a file attachment",
        description = "Remove a file attachment from the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "File deleted successfully",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "File not found",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable
            @Parameter(
                description = "ID of the file to delete",
                required = true,
                schema = @Schema(type = "integer", format = "int64")
            ) 
            Long id) {
        fileAttachmentService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
}