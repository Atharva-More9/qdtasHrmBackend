package com.qdtas.service.impl;

import com.qdtas.entity.LeaveCount;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.LeaveCountRepository;
import com.qdtas.service.LeaveCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveCountServiceImpl implements LeaveCountService {

    @Autowired
    LeaveCountRepository leaveCountRepository;

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
        // Add other fields as needed

        return leaveCountRepository.save(existingLeaveCount);
    }

    @Override
    public LeaveCount getAllLeaveCount() {
        List<LeaveCount> leaveCounts = leaveCountRepository.findAll();
        LeaveCount totalLeaveCount = new LeaveCount();
        for (LeaveCount leaveCount : leaveCounts) {
            totalLeaveCount.setCasualLeaves(totalLeaveCount.getCasualLeaves() + leaveCount.getCasualLeaves());
            totalLeaveCount.setSickLeaves(totalLeaveCount.getSickLeaves() + leaveCount.getSickLeaves());
            // Sum other fields as needed
        }
        return totalLeaveCount;
    }

    @Override
    public LeaveCount getCasualLeaveCount() {
        List<LeaveCount> leaveCounts = leaveCountRepository.findAll();
        LeaveCount casualLeaveCount = new LeaveCount();
        int totalCasualLeaves = leaveCounts.stream()
                .mapToInt(LeaveCount::getCasualLeaves)
                .sum();
        casualLeaveCount.setCasualLeaves(totalCasualLeaves);
        return casualLeaveCount;
    }

    @Override
    public LeaveCount getSickLeaveCount() {
        List<LeaveCount> leaveCounts = leaveCountRepository.findAll();
        LeaveCount sickLeaveCount = new LeaveCount();
        int totalSickLeaves = leaveCounts.stream()
                .mapToInt(LeaveCount::getSickLeaves)
                .sum();
        sickLeaveCount.setSickLeaves(totalSickLeaves);
        return sickLeaveCount;
    }
}
