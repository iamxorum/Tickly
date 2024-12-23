package com.xrm.tickly.ticketing_app.service;

import com.xrm.tickly.ticketing_app.entity.Ticket;

import java.util.List;

public interface TicketServiceInt {
    List<Ticket> getAllTickets();
    Ticket createTicket(Ticket ticket);
    Ticket getTicketById(Long id);
    Ticket updateTicket(Long id, Ticket ticketDetails);
    void deleteTicket(Long id);
}

