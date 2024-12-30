package com.xrm.tickly.ticketing_app.service;

import com.xrm.tickly.ticketing_app.dto.CommentDTO;
import com.xrm.tickly.ticketing_app.exception.ResourceNotFoundException;
import com.xrm.tickly.ticketing_app.model.Comment;
import com.xrm.tickly.ticketing_app.model.Ticket;
import com.xrm.tickly.ticketing_app.model.User;
import com.xrm.tickly.ticketing_app.repository.CommentRepository;
import com.xrm.tickly.ticketing_app.repository.TicketRepository;
import com.xrm.tickly.ticketing_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public Page<CommentDTO> getTicketComments(Long ticketId, Pageable pageable) {
        return commentRepository.findByTicketId(ticketId, pageable)
                .map(this::convertToDTO);
    }

    @Transactional
    public CommentDTO createComment(Long ticketId, Long userId, CommentDTO commentDTO) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setTicket(ticket);
        comment.setUser(user);

        return convertToDTO(commentRepository.save(comment));
    }

    @Transactional
    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        
        comment.setContent(commentDTO.getContent());
        return convertToDTO(commentRepository.save(comment));
    }

    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found");
        }
        commentRepository.deleteById(commentId);
    }

    private CommentDTO convertToDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .username(comment.getUser().getUsername())
                .ticketId(comment.getTicket().getId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}