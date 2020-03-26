package com.xh.website.module.project.controller;


import com.xh.website.module.project.entity.Project;
import com.xh.website.module.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


/**
 * @author qixiaohui
 * @descreption
 * @date 2018/12/21
 */

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository){this.projectRepository=projectRepository;}

    @GetMapping("/projectlist")
    public String getAll(Model model){
        List<Project> projectList = new ArrayList<>();
        projectList = projectRepository.findAll();
        model.addAttribute( "projectList", projectList);
        return "research_project";
    }
}
