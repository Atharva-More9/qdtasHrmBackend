package com.qdtas.service.impl;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.Job;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.JobRepository;
import com.qdtas.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Job create(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Job updateById(long jobId, Job job) {
        Job oldJob = jobRepository.findById(jobId).get();
        oldJob.setJobName(job.getJobName());
        return jobRepository.save(oldJob);
    }

    @Override
    public JsonMessage deleteById(long jobId) {
        try{
            jobRepository.deleteById(jobId);
            return new JsonMessage("Job deleted successfully");
        }
        catch(Exception exe) {
            return new JsonMessage("Something Went Wrong");
        }
    }

    @Override
    public Job getById(long jobId) {
        return jobRepository.findById(jobId).orElseThrow(()->new ResourceNotFoundException("Job","JobId",String.valueOf(jobId)));
    }

    @Override
    public List<Job> getAll(int pgn, int size){
        return jobRepository.findAll(PageRequest.of(pgn, size, Sort.by(Sort.Direction.ASC, "jobName"))).stream().collect(Collectors.toList());
    }

}
