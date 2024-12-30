package com.xrm.tickly.ticketing_app.repository;

import com.xrm.tickly.ticketing_app.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByTicketId(Long ticketId, Pageable pageable);
}