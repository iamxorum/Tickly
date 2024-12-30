package com.xrm.tickly.ticketing_app_test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xrm.tickly.ticketing_app.controller.ProjectController;
import com.xrm.tickly.ticketing_app.dto.ProjectDTO;
import com.xrm.tickly.ticketing_app.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenGetAllProjects_thenReturnJsonArray() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Test Project");

        when(projectService.getAllProjects()).thenReturn(List.of(projectDTO));

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Project"));
    }

    @Test
    void whenCreateProject_thenReturnCreatedProject() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("New Project");

        when(projectService.createProject(any(ProjectDTO.class))).thenReturn(projectDTO);

        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Project"));
    }

    @Test
    void whenCreateInvalidProject_thenReturn400() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        // Name is required but not set

        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectDTO)))
                .andExpect(status().isBadRequest());
    }
}