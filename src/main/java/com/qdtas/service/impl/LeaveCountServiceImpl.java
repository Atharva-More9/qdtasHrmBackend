package com.qdtas.service.impl;

import com.qdtas.entity.LeaveCount;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.LeaveCountRepository;
import com.qdtas.repository.UserRepository;
import com.qdtas.service.LeaveCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveCountServiceImpl implements LeaveCountService {

    @Autowired
    private LeaveCountRepository leaveCountRepository;

    @Autowired
    private UserRepository userRepository; // Assume you have a UserRepository to fetch User data

    @Override
    public LeaveCount addLeaveCount(LeaveCount leaveCount) {
        return leaveCountRepository.save(leaveCount);
    }

    @Override
    public LeaveCount updateLeaveCount(long id, LeaveCount leaveCount) {
        LeaveCount existingLeaveCount = leaveCountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveCount", "id", String.valueOf(id)));

        existingLeaveCount.setCasualLeaves(leaveCount.getCasualLeaves());
        existingLeaveCount.setSickLeaves(leaveCount.getSickLeaves());
        existingLeaveCount.setTotalLeaves(leaveCount.getTotalLeaves());

        return leaveCountRepository.save(existingLeaveCount);
    }

    @Override
    public LeaveCount getLeaveCountByUserId(long Id) {
        return leaveCountRepository.findByUserId(Id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveCount", "userId", String.valueOf(Id)));
    }

    @Override
    public void adjustLeaveCount(long Id, int leaveDays, boolean isApproved) {
        LeaveCount leaveCount = leaveCountRepository.findByUserId(Id)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveCount", "userId", String.valueOf(Id)));

        if (isApproved) {
            leaveCount.setTotalLeaves(leaveCount.getTotalLeaves() - leaveDays);
        } else {
            leaveCount.setTotalLeaves(leaveCount.getTotalLeaves() + leaveDays);
        }
        leaveCountRepository.save(leaveCount);
    }
}
