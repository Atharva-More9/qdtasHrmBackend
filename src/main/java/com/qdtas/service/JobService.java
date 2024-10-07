package com.qdtas.service;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.Job;

import java.util.List;

public interface JobService {

    public Job create(Job job);

    public Job updateById(long jobId, Job job);

    public JsonMessage deleteById(long jobId);

    public Job getById(long jobId);

    public List<Job> getAll(int pgn, int size);

    public int getTotalCount();
}
