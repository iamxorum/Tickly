package com.xrm.tickly.ticketing_app.repository;

import com.xrm.tickly.ticketing_app.model.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {
    List<FileAttachment> findByTicketId(Long ticketId);
    List<FileAttachment> findByUploadedBy_Id(Long userId);   
}