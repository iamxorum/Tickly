package com.xrm.tickly.ticketing_app.service;

import com.xrm.tickly.ticketing_app.dto.FileAttachmentDTO;
import com.xrm.tickly.ticketing_app.model.FileAttachment;
import com.xrm.tickly.ticketing_app.repository.FileAttachmentRepository;
import com.xrm.tickly.ticketing_app.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileAttachmentRepository fileRepository;
    private final Path fileStorageLocation = Paths.get("uploads");

    @Transactional
    public FileAttachmentDTO storeFile(Long ticketId, MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            FileAttachment attachment = new FileAttachment();
            attachment.setFileName(fileName);
            attachment.setFileType(file.getContentType());
            attachment.setSize(file.getSize());
            
            return convertToDTO(fileRepository.save(attachment));
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file", ex);
        }
    }

    public FileAttachmentDTO getFile(Long fileId) {
        return fileRepository.findById(fileId)
            .map(this::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException("File not found"));
    }

    public Resource loadFileAsResource(Long fileId) {
        try {
            FileAttachment file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));
            Path filePath = fileStorageLocation.resolve(file.getFileName());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found");
            }
        } catch (IOException ex) {
            throw new RuntimeException("File not found", ex);
        }
    }

    @Transactional
    public void deleteFile(Long fileId) {
        FileAttachment file = fileRepository.findById(fileId)
            .orElseThrow(() -> new ResourceNotFoundException("File not found"));
            
        try {
            Path filePath = fileStorageLocation.resolve(file.getFileName());
            Files.deleteIfExists(filePath);
            fileRepository.delete(file);
        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file", ex);
        }
    }

    private FileAttachmentDTO convertToDTO(FileAttachment file) {
        FileAttachmentDTO dto = new FileAttachmentDTO();
        dto.setId(file.getId());
        dto.setFileName(file.getFileName());
        dto.setFileType(file.getFileType());
        dto.setSize(file.getSize());
        dto.setTicketId(file.getTicket() != null ? file.getTicket().getId() : null);
        dto.setCreatedAt(file.getCreatedAt());
        dto.setUpdatedAt(file.getUpdatedAt());
        return dto;
    }
} 