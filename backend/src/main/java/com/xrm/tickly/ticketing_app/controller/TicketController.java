package com.xrm.tickly.ticketing_app.controller;

import com.xrm.tickly.ticketing_app.entity.Ticket;
import com.xrm.tickly.ticketing_app.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<Ticket> getTickets() {
        // Fetch all tickets from the database
        return ticketRepository.findAll();
    }
}

