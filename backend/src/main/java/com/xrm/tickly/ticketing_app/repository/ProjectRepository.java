package com.xrm.tickly.ticketing_app.repository;

import com.xrm.tickly.ticketing_app.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByName(String name);
}