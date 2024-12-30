package com.xrm.tickly.ticketing_app.repository;

import com.xrm.tickly.ticketing_app.model.Ticket;
import com.xrm.tickly.ticketing_app.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByAssigneeId(Long assigneeId);
    List<Ticket> findByReporterId(Long reporterId);
    List<Ticket> findByStatus(TicketStatus status);
}