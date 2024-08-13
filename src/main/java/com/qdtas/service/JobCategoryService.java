package com.qdtas.service;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.JobCategory;

import java.util.List;

public interface JobCategoryService {

    public JobCategory createJobCategory(JobCategory jobCategory);

    public JobCategory updateById(Long id, JobCategory jobCategory);

    public JsonMessage deleteById(Long id);

    public JobCategory getById(Long id);

    public List<JobCategory> getAll(int pgn, int size);
}
