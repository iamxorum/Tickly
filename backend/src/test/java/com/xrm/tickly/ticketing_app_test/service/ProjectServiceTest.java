package com.xrm.tickly.ticketing_app_test.service;

import com.xrm.tickly.ticketing_app.dto.ProjectDTO;
import com.xrm.tickly.ticketing_app.model.Project;
import com.xrm.tickly.ticketing_app.model.User;
import com.xrm.tickly.ticketing_app.enums.ProjectStatus;
import com.xrm.tickly.ticketing_app.repository.ProjectRepository;
import com.xrm.tickly.ticketing_app.repository.UserRepository;
import com.xrm.tickly.ticketing_app.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private ProjectDTO projectDTO;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setStatus(ProjectStatus.ACTIVE);

        projectDTO = new ProjectDTO();
        projectDTO.setName("Test Project");
        projectDTO.setStatus(ProjectStatus.ACTIVE);
    }

    @Test
    void whenGetAllProjects_thenReturnProjectList() {
        when(projectRepository.findAll()).thenReturn(List.of(project));

        List<ProjectDTO> found = projectService.getAllProjects();

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getName()).isEqualTo(project.getName());
        verify(projectRepository).findAll();
    }

    @Test
    void whenCreateProject_thenReturnSavedProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDTO saved = projectService.createProject(projectDTO);

        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo(projectDTO.getName());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void whenAddMemberToProject_thenReturnUpdatedProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDTO updated = projectService.addMemberToProject(1L, 1L);

        assertThat(updated).isNotNull();
        verify(projectRepository).save(any(Project.class));
    }
}