package com.xrm.tickly.ticketing_app.service;

import com.xrm.tickly.ticketing_app.dto.ProjectDTO;
import com.xrm.tickly.ticketing_app.model.Project;
import com.xrm.tickly.ticketing_app.repository.ProjectRepository;
import com.xrm.tickly.ticketing_app.repository.UserRepository;
import com.xrm.tickly.ticketing_app.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xrm.tickly.ticketing_app.model.User;
import com.xrm.tickly.ticketing_app.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return convertToDTO(project);
    }

    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = convertToEntity(projectDTO);
        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    @Transactional
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(projectDTO.getStatus());
        
        if (projectDTO.getMemberIds() != null && !projectDTO.getMemberIds().isEmpty()) {
            Set<User> members = projectDTO.getMemberIds().stream()
                .map(memberId -> userRepository.findById(memberId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + memberId)))
                .collect(Collectors.toSet());
            project.setMembers(members);
        }
        
        return convertToDTO(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }

    @Transactional
    public ProjectDTO addMemberToProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        project.getMembers().add(user);
        return convertToDTO(projectRepository.save(project));
    }

    public List<UserDTO> getProjectMembers(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
        
        return project.getMembers().stream()
            .map(this::convertUserToDTO)
            .collect(Collectors.toList());
    }

    private ProjectDTO convertToDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStatus(project.getStatus());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());
        dto.setMemberIds(project.getMembers().stream()
            .map(User::getId)
            .collect(Collectors.toSet()));
        return dto;
    }

    private Project convertToEntity(ProjectDTO dto) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStatus(dto.getStatus());
        return project;
    }

    private UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole())
            .build();
    }
}