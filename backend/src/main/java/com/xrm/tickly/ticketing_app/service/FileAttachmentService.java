package com.xrm.tickly.ticketing_app.service;

import com.xrm.tickly.ticketing_app.dto.FileAttachmentDTO;
import com.xrm.tickly.ticketing_app.model.FileAttachment;
import com.xrm.tickly.ticketing_app.model.Ticket;
import com.xrm.tickly.ticketing_app.model.User;
import com.xrm.tickly.ticketing_app.repository.FileAttachmentRepository;
import com.xrm.tickly.ticketing_app.repository.TicketRepository;
import com.xrm.tickly.ticketing_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileAttachmentService {

    private final FileAttachmentRepository fileAttachmentRepository;
    private final FileStorageService fileStorageService;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Autowired
    public FileAttachmentService(
            FileAttachmentRepository fileAttachmentRepository,
            FileStorageService fileStorageService,
            TicketRepository ticketRepository,
            UserRepository userRepository) {
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.fileStorageService = fileStorageService;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FileAttachmentDTO uploadFile(MultipartFile file, Long ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String storedFileName = fileStorageService.storeFile(file);

        FileAttachment attachment = new FileAttachment();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setSize(file.getSize());
        attachment.setFilePath(storedFileName);
        attachment.setTicket(ticket);
        attachment.setUploadedBy(user);

        FileAttachment savedAttachment = fileAttachmentRepository.save(attachment);
        return convertToDTO(savedAttachment);
    }

    public List<FileAttachmentDTO> getFilesByTicketId(Long ticketId) {
        return fileAttachmentRepository.findByTicketId(ticketId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFile(Long fileId) {
        FileAttachment attachment = fileAttachmentRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
        
        fileStorageService.deleteFile(attachment.getFilePath());
        fileAttachmentRepository.delete(attachment);
    }

    private FileAttachmentDTO convertToDTO(FileAttachment attachment) {
        FileAttachmentDTO dto = new FileAttachmentDTO();
        dto.setId(attachment.getId());
        dto.setFileName(attachment.getFileName());
        dto.setFileType(attachment.getFileType());
        dto.setSize(attachment.getSize());
        dto.setTicketId(attachment.getTicket().getId());
        dto.setUploadedById(attachment.getUploadedBy().getId());
        dto.setCreatedAt(attachment.getCreatedAt());
        dto.setUpdatedAt(attachment.getUpdatedAt());
        return dto;
    }
}