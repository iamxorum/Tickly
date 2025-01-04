package com.xrm.tickly.ticketing_app.controller;

import com.xrm.tickly.ticketing_app.dto.TicketDTO;
import com.xrm.tickly.ticketing_app.enums.TicketStatus;
import com.xrm.tickly.ticketing_app.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Tag(name = "Ticket Management", description = "APIs for managing tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    @Operation(summary = "Get all tickets", description = "Retrieves all tickets with optional filtering and pagination")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved tickets"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view tickets")
    })
    public ResponseEntity<Page<TicketDTO>> getAllTickets(
            @Parameter(description = "Page number (starts from 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ticketService.getAllTickets(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by ID", description = "Retrieves a specific ticket by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ticket found successfully"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to view this ticket")
    })
    public ResponseEntity<TicketDTO> getTicketById(
            @Parameter(description = "ID of the ticket to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PostMapping
    @Operation(summary = "Create new ticket", description = "Creates a new ticket in the system")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid ticket data"),
        @ApiResponse(responseCode = "403", description = "Not authorized to create tickets")
    })
    public ResponseEntity<TicketDTO> createTicket(
            @Parameter(description = "Ticket details") @Valid @RequestBody TicketDTO ticketDTO) {
        return ResponseEntity.status(201).body(ticketService.createTicket(ticketDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update ticket", description = "Updates an existing ticket's information")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ticket updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid ticket data"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to update this ticket")
    })
    public ResponseEntity<TicketDTO> updateTicket(
            @Parameter(description = "ID of the ticket to update") @PathVariable Long id,
            @Parameter(description = "Updated ticket details") @Valid @RequestBody TicketDTO ticketDTO) {
        ticketDTO.setId(id);
        return ResponseEntity.ok(ticketService.updateTicket(id, ticketDTO));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update ticket status", description = "Updates the status of an existing ticket")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ticket status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid status"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to update ticket status")
    })
    public ResponseEntity<TicketDTO> updateTicketStatus(
            @Parameter(description = "ID of the ticket") @PathVariable Long id,
            @Parameter(description = "New status") @RequestParam TicketStatus status) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ticket", description = "Deletes an existing ticket")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ticket deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Ticket not found"),
        @ApiResponse(responseCode = "403", description = "Not authorized to delete this ticket")
    })
    public ResponseEntity<Void> deleteTicket(
            @Parameter(description = "ID of the ticket to delete") @PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}