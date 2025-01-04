package com.xrm.tickly.ticketing_app.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.xrm.tickly.ticketing_app.enums.ProjectStatus;

class ProjectTest {

    @Test
    void testProject() {
        Project project = new Project();
        
         
        project.setId(1L);
        project.setName("Test Project");
        project.setDescription("This is a test project.");
        project.setStatus(ProjectStatus.ACTIVE);
        
         
        assertEquals(1L, project.getId());
        assertEquals("Test Project", project.getName());
        assertEquals("This is a test project.", project.getDescription());
        assertEquals(ProjectStatus.ACTIVE, project.getStatus());
    }
} 