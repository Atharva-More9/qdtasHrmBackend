package com.qdtas.service;

import com.qdtas.dto.LeaveDTO;
import com.qdtas.entity.Leave;

import java.util.Date;
import java.util.List;

public interface LeaveService {

    List<Leave> getLeaveByEmpId(Long id);

    List<Leave> getAllLeaveRequests(int pgn, int size);

    Leave createLeaveRequest(long empId, LeaveDTO leaveRequest);

    Leave updateLeaveRequest(Long id, LeaveDTO updatedLeaveRequest);

    void deleteLeaveRequest(Long id);

    Leave approveLeaveRequest(Long id);

    Leave rejectLeaveRequest(Long id);

    boolean hasApprovedLeaveOnDates(long empId, Date startDate, Date endDate);
}
