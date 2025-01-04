package com.xrm.tickly.ticketing_app.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.xrm.tickly.ticketing_app.enums.TicketStatus;

class TicketTest {

    @Test
    void testTicket() {
        Ticket ticket = new Ticket();
        
         
        ticket.setId(1L);
        ticket.setTitle("Test Ticket");
        ticket.setDescription("This is a test ticket.");
        ticket.setStatus(TicketStatus.OPEN);
        
         
        assertEquals(1L, ticket.getId());
        assertEquals("Test Ticket", ticket.getTitle());
        assertEquals("This is a test ticket.", ticket.getDescription());
        assertEquals(TicketStatus.OPEN, ticket.getStatus());
    }
}