package com.qdtas.service.impl;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.Department;
import com.qdtas.entity.User;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.DepartmentRepository;
import com.qdtas.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository drp;

    @Override
    public Department create(Department dp) {
        return drp.save(dp);
    }

    @Override
    public Department getById(long deptId) {
        return drp.findById(deptId).orElseThrow(()-> new ResourceNotFoundException("Department","department_id",String.valueOf(deptId)));
    }

    @Override
    public Department updateById(long deptId, Department dp) {
        Department d = drp.getById(deptId);
        d.setDeptName(dp.getDeptName());
        return drp.save(d);
    }

    @Override
    public JsonMessage deleteById(long deptId) {
        try {
            drp.deleteById(deptId);
            return new JsonMessage("Department Deleted Successfully");
        }
        catch(Exception exe){
            return new JsonMessage("Something Went Wrong");
        }
    }

    @Override
    public Set<Department> getAllDepartments(int pgn, int size) {
        return drp.findAll(PageRequest.of(pgn, size, Sort.by(Sort.Direction.ASC, "deptName"))).stream().collect(Collectors.toSet());
    }


    @Override
    public List<User> getAllUsers(long deptId) {
        Department d = getById(deptId);
        return new ArrayList<>(d.getEmployees());
    }
}
