package com.qdtas.service;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.EmploymentStatus;

import java.util.List;

public interface EmploymentStatusService {

    public EmploymentStatus create(EmploymentStatus employmentStatus);

    public EmploymentStatus updateById(Long id, EmploymentStatus employmentStatus);

    public EmploymentStatus getById(Long id);

    public JsonMessage deleteById(Long id);

    public List<EmploymentStatus> getAll(int pgn, int size);
}
