package com.xrm.tickly.ticketing_app.controller;

import com.xrm.tickly.ticketing_app.dto.CommentDTO;
import com.xrm.tickly.ticketing_app.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.xrm.tickly.ticketing_app.model.User;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/tickets/{ticketId}/comments")
@RequiredArgsConstructor
@Tag(name = "Comment Management", description = "APIs for managing ticket comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    @Operation(
        summary = "Get ticket comments",
        description = "Retrieves all comments for a specific ticket with pagination"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view comments")
    })
    public ResponseEntity<Page<CommentDTO>> getTicketComments(
            @Parameter(description = "ID of the ticket to get comments for") 
            @PathVariable Long ticketId,
            @Parameter(description = "Pagination parameters") 
            Pageable pageable) {
        return ResponseEntity.ok(commentService.getTicketComments(ticketId, pageable));
    }

    @PostMapping
    @Operation(
        summary = "Create comment",
        description = "Creates a new comment for a specific ticket"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comment created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid comment data"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to create comments")
    })
    public ResponseEntity<CommentDTO> createComment(
            @Parameter(description = "ID of the ticket to create comment for") 
            @PathVariable Long ticketId,
            @Parameter(description = "Comment details") 
            @Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.createComment(ticketId, getCurrentUserId(), commentDTO));
    }

    @PutMapping("/{commentId}")
    @Operation(
        summary = "Update comment",
        description = "Updates an existing comment"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid comment data"),
        @ApiResponse(responseCode = "404", description = "Comment not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to update comment")
    })
    public ResponseEntity<CommentDTO> updateComment(
            @Parameter(description = "ID of the ticket") 
            @PathVariable Long ticketId,
            @Parameter(description = "ID of the comment to update") 
            @PathVariable Long commentId,
            @Parameter(description = "Updated comment details") 
            @Valid @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.updateComment(commentId, commentDTO));
    }

    @DeleteMapping("/{commentId}")
    @Operation(
        summary = "Delete comment",
        description = "Deletes an existing comment"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Comment not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to delete comment")
    })
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "ID of the ticket") 
            @PathVariable Long ticketId,
            @Parameter(description = "ID of the comment to delete") 
            @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}