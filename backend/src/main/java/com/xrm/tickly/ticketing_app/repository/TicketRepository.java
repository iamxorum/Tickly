package com.xrm.tickly.ticketing_app.repository;


import com.xrm.tickly.ticketing_app.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
