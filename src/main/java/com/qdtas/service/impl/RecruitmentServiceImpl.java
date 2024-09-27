package com.qdtas.service.impl;

import com.qdtas.dto.JsonMessage;
import com.qdtas.dto.UpdateRecruitmentDto;
import com.qdtas.entity.Job;
import com.qdtas.entity.Recruitment;
import com.qdtas.entity.User;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.JobRepository;
import com.qdtas.repository.RecruitmentRepository;
import com.qdtas.repository.UserRepository;
import com.qdtas.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Recruitment create(Recruitment recruitment) {
        return recruitmentRepository.save(recruitment);
    }

    @Override
    public Recruitment getById(long recruitmentId) {
        return recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Recruitment", "recruitment_id", String.valueOf(recruitmentId)));
    }

    @Override
    public List<Recruitment> getAllRecruitments(int page, int size) {
        return recruitmentRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))).getContent();
    }

    @Override
    public Recruitment updateById(long Id, UpdateRecruitmentDto ur) {

        Recruitment existingRecruitment = getById(Id);

        // Update Job if present
        if (ur.getJob() != 0) {
            Job job = jobRepository.findById(ur.getJob())
                    .orElseThrow(() -> new ResourceNotFoundException("Job", "job_id", String.valueOf(ur.getJob())));
            existingRecruitment.setJob(job);
        }

        // Update Experience if provided
        if (ur.getExperience() != 0) {
            existingRecruitment.setExperience(ur.getExperience());
        }

        // Update User if present
        if (ur.getUser() != 0) {
            User user = userRepository.findById(ur.getUser())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "user_id", String.valueOf(ur.getUser())));
            existingRecruitment.setUser(user);
        }

        // Update Status if present
        if (ur.getStatus() != null) {
            existingRecruitment.setStatus(ur.getStatus());
        }

        // Save and return updated recruitment
        return recruitmentRepository.save(existingRecruitment);
    }

    @Override
    public JsonMessage deleteById(long recruitmentId) {
        Recruitment recruitment = getById(recruitmentId);
        recruitmentRepository.delete(recruitment);
        return new JsonMessage("Recruitment deleted successfully");
    }
}
