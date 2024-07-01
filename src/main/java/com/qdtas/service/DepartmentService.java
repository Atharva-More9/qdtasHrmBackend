package com.qdtas.service;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.Department;
import com.qdtas.entity.User;

import java.util.List;
import java.util.Set;

public interface DepartmentService {

    public Department create(Department dp);

    public Department getById(long deptId);


    public Department updateById(long deptId,Department dp);

    public JsonMessage deleteById(long deptId);

    List<Department> getAllDepartments(int pgn, int size);

    public List<User> getAllUsers(long DeptId);

}
