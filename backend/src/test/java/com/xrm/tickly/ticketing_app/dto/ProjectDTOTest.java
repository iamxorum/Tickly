package com.xrm.tickly.ticketing_app.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.xrm.tickly.ticketing_app.enums.ProjectStatus;

class ProjectDTOTest {

    @Test
    void testProjectDTO() {
        ProjectDTO projectDTO = new ProjectDTO();
        
         
        projectDTO.setId(1L);
        projectDTO.setName("Test Project");
        projectDTO.setDescription("This is a test project.");
        projectDTO.setStatus(ProjectStatus.ACTIVE);
        
         
        assertEquals(1L, projectDTO.getId());
        assertEquals("Test Project", projectDTO.getName());
        assertEquals("This is a test project.", projectDTO.getDescription());
        assertEquals(ProjectStatus.ACTIVE, projectDTO.getStatus());
    }
}