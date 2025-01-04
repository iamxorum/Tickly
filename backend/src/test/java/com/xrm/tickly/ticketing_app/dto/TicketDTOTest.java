package com.xrm.tickly.ticketing_app.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.xrm.tickly.ticketing_app.enums.TicketStatus;

class TicketDTOTest {

    @Test
    void testTicketDTO() {
        TicketDTO ticketDTO = new TicketDTO();
        
         
        ticketDTO.setId(1L);
        ticketDTO.setTitle("Test Ticket");
        ticketDTO.setDescription("This is a test ticket.");
        ticketDTO.setStatus(TicketStatus.OPEN);
        
         
        assertEquals(1L, ticketDTO.getId());
        assertEquals("Test Ticket", ticketDTO.getTitle());
        assertEquals("This is a test ticket.", ticketDTO.getDescription());
        assertEquals(TicketStatus.OPEN, ticketDTO.getStatus());
    }
} 