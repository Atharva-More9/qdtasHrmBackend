package com.qdtas.service;

import com.qdtas.dto.ProjectDTO;
import com.qdtas.entity.Project;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {

    public Project addProject(ProjectDTO pd);

    public Project updateProject(ProjectDTO pd,long pId);

    public void deleteProject(long pId);

    public Project getProjectById( long pId);

    public List<Project> searchProjectByName(String keyword, int pgn,int size);

    public Project assignEmployee(long userId,long pId);

    public Project assignEmployees(List<Long> employeeIds,long pId);

    public List<Project> getAllProjects(int pgn,int size);

    public Project removeEmployee(long empId,long pId);

    public Project removeAll(List<Long> empIds,long pId);

    public Project assignManager(long userId , long pId);

    public Project assignManagers(List<Long> employeeIds,long pId);

    public Project removeManager(long empId,long pId);

    public Project removeManagers(List<Long> empIds,long pId);

    public int getTotalCount();
}
