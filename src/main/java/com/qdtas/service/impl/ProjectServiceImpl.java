package com.qdtas.service.impl;

import com.qdtas.dto.ProjectDTO;
import com.qdtas.entity.Project;
import com.qdtas.entity.User;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.ProjectRepository;
import com.qdtas.repository.UserRepository;
import com.qdtas.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository prp;

    @Autowired
    private UserRepository urp;

    @Override
    public Project addProject(ProjectDTO pd) {
        Project p = new Project();
        p.setProjectName(pd.getProjectName());
        p.setClient(pd.getClient());
        p.setDescription(pd.getDescription());
        p.setStatus(pd.getStatus());
        p.setType(pd.getType());
        return prp.save(p);
    }

    @Override
    public Project updateProject(ProjectDTO pd, long projectId) {
        Project p = prp.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "Project ID", String.valueOf(projectId)));
            p.setProjectName(pd.getProjectName());
            p.setType(pd.getType());
            p.setDescription(pd.getDescription());
            p.setClient(pd.getClient());
            p.setStatus(pd.getStatus());

        return prp.save(p);
    }

    @Override
    public Project getProjectById(long pId) {
        return prp.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Project", "Project ID", String.valueOf(pId)));
    }

    @Override
    public List<Project> searchProjectByName(String keyword, int pgn, int size) {
        return prp.findProjectByName(keyword, PageRequest.of(pgn, size)).stream().collect(Collectors.toList());
    }

    @Override
    public Project assignEmployee(long userId, long pId) {
        Project project = prp.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Project", "ProjectId", String.valueOf(pId)));
        User u = urp.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserdId", String.valueOf(userId)));
        u.getProjects().add(project);
        project.getTeam().add(u);
        prp.save(project);
        urp.save(u);
        return project;
    }

    @Override
    public Project assignEmployees(List<Long> employeeIds, long pId) {
        Project project = prp.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Project", "ProjectId", String.valueOf(pId)));
        List<User> employeesToAssign = urp.findAllById(employeeIds);
        project.getTeam().addAll(employeesToAssign);
        for (User us : employeesToAssign) {
            us.getProjects().add(project);
        }
        prp.save(project);
        urp.saveAll(employeesToAssign);
        return project;
    }


    @Override
    public List<Project> getAllProjects(int pgn, int size) {
        return prp.findAll(PageRequest.of(pgn, size, Sort.by(Sort.Direction.ASC, "projectName"))).stream().toList();
    }

    @Override
    public Project removeEmployee(long empId, long pId) {
        Project project = prp.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Project", "ProjectId", String.valueOf(pId)));
        User u = urp.findById(empId).orElseThrow(() -> new ResourceNotFoundException("User", "UserdId", String.valueOf(empId)));
        project.getTeam().remove(u);
        u.getProjects().remove(project);
        prp.save(project);
        urp.save(u);
        return project;
    }

    @Override
    public Project removeAll(List<Long> empIds, long pId) {
        Project project = prp.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Project", "ProjectId", String.valueOf(pId)));
        List<User> uList = urp.findAllById(empIds);
        project.getTeam().removeAll(uList);
        for (User us : uList) {
            us.getProjects().remove(project);
        }
        prp.save(project);
        urp.saveAll(uList);
        return project;
    }

    @Override
    public Project assignManager(long userId, long pId) {
        Project project = prp.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Project", "ProjectId", String.valueOf(pId)));

        if (project != null) {

            User manager = urp.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", String.valueOf(userId)));

            if (manager != null) {

                project.getManagers().add(manager);

            }
        }
        return prp.save(project);
    }

    @Override
    public Project assignManagers(List<Long> managerIds, long pId) {
        Project project = prp.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Project", "ProjectId", String.valueOf(pId)));

        if (project != null) {

            for (Long managerId : managerIds) {

                User manager = urp.findById(managerId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", String.valueOf(managerIds)));

                if (manager != null) {

                    project.getManagers().add(manager);

                }
            }
        }
        return prp.save(project);
    }

    @Override
    public Project removeManager(long empId, long pId) {
        Project project = prp.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Project", "ProjectId", String.valueOf(pId)));

        if (project != null) {

            User managerToremove = urp.findById(empId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", String.valueOf(empId)));

            if (managerToremove != null) {

                project.getManagers().remove(managerToremove);

            }
        }
        return prp.save(project);
    }

    @Override
    public Project removeManagers(List<Long> empIds, long pId) {

        Project project = prp.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Project", "ProjectId", String.valueOf(pId)));


        if (project != null) {

            List<User> userList = urp.findAllById(empIds);

            if (!userList.isEmpty()) {

                project.getManagers().removeAll(userList);
            } else {

                throw new ResourceNotFoundException("User", "UserId", "No valid manager IDs found");
            }
        }

        return prp.save(project);
    }

    @Override
    public void deleteProject(long pId) {
        Project p = prp.findById(pId).orElseThrow(() -> new ResourceNotFoundException("Project", "Project ID", String.valueOf(pId)));
        prp.delete(p);
    }


}
