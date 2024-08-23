package com.qdtas.service.impl;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.JobCategory;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.JobCategoryRepository;
import com.qdtas.service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobCategoryServiceImpl implements JobCategoryService {

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Override
    public JobCategory createJobCategory(JobCategory jobCategory) {
        return jobCategoryRepository.save(jobCategory);
    }

    @Override
    public JobCategory updateById(Long id, JobCategory jobCategory) {
        JobCategory oldJobCategory = jobCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobCategory", "id", String.valueOf(id)));

        // Update only the fields that need to be changed
        oldJobCategory.setJobCategoryName(jobCategory.getJobCategoryName());
        // Add more fields if needed
        return jobCategoryRepository.save(oldJobCategory);
    }

    @Override
    public JsonMessage deleteById(Long id) {
        try{
            jobCategoryRepository.deleteById(id);
            return new JsonMessage("Successfully deleted JobCategory");
        }
        catch(Exception e){
            return new JsonMessage("Failed to delete JobCategory");
        }
    }

    @Override
    public JobCategory getById(Long id) {
        return jobCategoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("JobCategory", "id", String.valueOf(id)));
    }

    @Override
    public List<JobCategory> getAll(int pgn, int size) {
        return jobCategoryRepository.findAll(PageRequest.of(pgn, size, Sort.by(Sort.Direction.ASC, "JobCategoryName"))).stream().collect(Collectors.toList());
    }
}
