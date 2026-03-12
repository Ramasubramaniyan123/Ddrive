package com.service;

import com.dao.ProjectDAO;
import com.model.Project;

import java.util.List;

public class ProjectService {

    private ProjectDAO projectDAO = new ProjectDAO();

    public void createProject(Project project) {
        projectDAO.save(project);
    }

    public Project getProject(Long id) {
        return projectDAO.findById(id);
    }

    public List<Project> getAllProjects() {
        return projectDAO.findAll();
    }

    public void updateProject(Project project) {
        projectDAO.update(project);
    }

    public void deleteProject(Long id) {
        projectDAO.delete(id);
    }
}