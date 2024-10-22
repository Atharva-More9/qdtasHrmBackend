package com.qdtas.service.impl;

import com.qdtas.dto.LeaveDTO;
import com.qdtas.entity.Leave;
import com.qdtas.entity.Project;
import com.qdtas.entity.User;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.LeaveRepository;
import com.qdtas.service.LeaveCountService;
import com.qdtas.service.LeaveService;
import com.qdtas.service.UserService;
import com.qdtas.utils.LeaveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.temporal.ChronoUnit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRequestRepository;

    @Autowired
    private UserService usr;
    @Autowired
    private EmailService ems;

    @Autowired
    private LeaveCountService leaveCountService;
    @Autowired
    private LeaveRepository leaveRepository;

    @Override
    public List<Leave> getLeaveByEmpId(Long id) {
        return leaveRequestRepository.findAllByEmployeeId(id);

    }

    public List<Leave> getAllLeaveRequests(int pgn, int size) {
        return leaveRequestRepository.findAll(   PageRequest.of(pgn, size, Sort.by(Sort.Direction.DESC, "leaveId") )  )
                .stream().toList();
    }

    private boolean datesOverlap(Date startDate, Date endDate, LocalDate startDate1, LocalDate endDate1) {
        LocalDate start1 = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end1 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return !start1.isAfter(endDate1) && !startDate1.isAfter(end1);
    }

    private boolean hasOverlappingApprovedLeave(long empId, Date startDate, Date endDate) {
        List<Leave> approvedLeaves = leaveRequestRepository.findByEmployeeIdAndStatus(empId, LeaveStatus.APPROVED.name());
        for (Leave leave : approvedLeaves) {
            if (datesOverlap(leave.getStartDate(), leave.getEndDate(), startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                return true;
            }
        }
        return false;
    }

//    @Override
//    public Leave createLeaveRequest(long empId, LeaveDTO leaveRequest) {
//        // Check for overlapping approved leaves
//        if (hasOverlappingApprovedLeave(empId, leaveRequest.getStartDate(), leaveRequest.getEndDate())) {
//            throw new RuntimeException("The leave request overlaps with an approved leave.");
//        }
//
//        Leave l = new Leave();
//        l.setStatus(LeaveStatus.PENDING.name());
//        l.setReason(leaveRequest.getReason());
//        l.setType(leaveRequest.getType());
//
//        // Convert start and end dates to avoid timezone issues
//        LocalDate startDate = leaveRequest.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate endDate = leaveRequest.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//        l.setStartDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        l.setEndDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
//
//        User u = usr.getById(empId);
//        l.setEmployee(u);
//
//        // Adjust the leave count immediately after the request is made (deduct leaves)
//        leaveCountService.adjustLeaveCount(empId, leaveRequest.getTotalLeaves(), false);
//
//        // Save the leave request
//        Leave savedLeave = leaveRequestRepository.save(l);
//
//        // Notify managers
//        Set<Project> projects = u.getProjects();
//        Set<User> managerList = new HashSet<>();
//        for (Project p : projects) {
//            managerList.addAll(p.getManagers());
//        }
//        List<String> mEmails = new ArrayList<>();
//        for (User m : managerList) {
//            mEmails.add(m.getEmail());
//        }
//        ems.sendLeaveRequestEmail(mEmails, savedLeave);
//
//        return savedLeave;
//    }

    // Example usage during leave creation
    public Leave createLeaveRequest(long empId, LeaveDTO leaveRequest) {
        // Check for overlapping approved leaves
        if (hasOverlappingApprovedLeave(empId, leaveRequest.getStartDate(), leaveRequest.getEndDate())) {
            throw new RuntimeException("The leave request overlaps with an approved leave.");
        }

        LocalDate startDate = leaveRequest.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = leaveRequest.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        User u = usr.getById(empId);

        // Check if the employee has enough leaves
        if (u.getTotalLeaves() < daysBetween) {
            throw new IllegalArgumentException("Insufficient leave balance to request leave.");
        }

        Leave l = new Leave();
        l.setStatus(LeaveStatus.PENDING.name());
        l.setReason(leaveRequest.getReason());
        l.setType(leaveRequest.getType());
        l.setStartDate(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        l.setEndDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        l.setEmployee(u);

        // Save the leave request
        Leave savedLeave = leaveRequestRepository.save(l);

        // Notify managers (same logic as before)
        Set<Project> projects = u.getProjects();
        Set<User> managerList = new HashSet<>();
        for (Project p : projects) {
            managerList.addAll(p.getManagers());
        }
        List<String> mEmails = new ArrayList<>();
        for (User m : managerList) {
            mEmails.add(m.getEmail());
        }
        ems.sendLeaveRequestEmail(mEmails, savedLeave);

        return savedLeave;
    }



    public Leave updateLeaveRequest(Long id, LeaveDTO updatedLeaveRequest) {
        Leave existingLeaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        existingLeaveRequest.setEmployee(usr.getById(updatedLeaveRequest.getEmployeeId()));
        existingLeaveRequest.setStartDate(updatedLeaveRequest.getStartDate());
        existingLeaveRequest.setReason(updatedLeaveRequest.getReason());
        existingLeaveRequest.setEndDate(updatedLeaveRequest.getEndDate());
        existingLeaveRequest.setType(updatedLeaveRequest.getType());
        return leaveRequestRepository.save(existingLeaveRequest);
    }

    public Leave getLeaveById(long lId){
        return leaveRequestRepository.findById(lId).orElseThrow(()-> new ResourceNotFoundException("LeaveRequest","LeaveID",String.valueOf(lId)));
    }

    public void deleteLeaveRequest(Long id) {
        Leave l = getLeaveById(id);
        leaveRequestRepository.delete(l);
    }


@PreAuthorize("hasRole('ADMIN')")
public Leave approveLeaveRequest(Long id) {
    Leave leaveRequest = leaveRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Leave request not found"));

    leaveRequest.setStatus(LeaveStatus.APPROVED.name());

    // Calculate the number of days between startDate and endDate
    LocalDate startDate = leaveRequest.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate endDate = leaveRequest.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1; // Include both start and end dates

    // Check if the employee has enough leaves
    User employee = leaveRequest.getEmployee();
    if (employee.getTotalLeaves() < daysBetween) {
        throw new IllegalArgumentException("Insufficient leave balance. Cannot approve leave.");
    }

    // Adjust the total leaves based on the approved leave request
    employee.setTotalLeaves(employee.getTotalLeaves() - (int) daysBetween);

    leaveRequestRepository.save(leaveRequest); // Save the updated leave request
    return leaveRequest;
}

    @PreAuthorize("hasRole('ADMIN')")
    public Leave rejectLeaveRequest(Long id) {
        Leave leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leaveRequest.setStatus(LeaveStatus.REJECTED.name());

        // No adjustment to leave count since the leave is rejected
        leaveRequestRepository.save(leaveRequest); // Save the updated leave request
        return leaveRequest;
    }
    @Override
    public int getLeaveCountByEmpId(Long id) {
        return leaveRequestRepository.getTotalLeavesByUserId(id);
    }

    @Override
    public int getTotalCount() {
        return (int)leaveRepository.count();
    }
}
