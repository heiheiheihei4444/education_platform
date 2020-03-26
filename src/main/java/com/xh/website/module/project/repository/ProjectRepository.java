package com.xh.website.module.project.repository;

import com.xh.website.module.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}

