package com.qdtas.service;

import com.qdtas.dto.LeaveDTO;
import com.qdtas.entity.Leave;

import java.util.List;

public interface LeaveService {

    public List<Leave> getLeaveByEmpId(Long id);

    public List<Leave> getAllLeaveRequests(int pgn,int size);

    public Leave createLeaveRequest(long empId,LeaveDTO leaveRequest);

    public Leave updateLeaveRequest(Long id, LeaveDTO updatedLeaveRequest);

    public void deleteLeaveRequest(Long id);

    public Leave approveLeaveRequest(Long id);

    public Leave rejectLeaveRequest(Long id);

    public int getLeaveCountByEmpId(Long id);

    public int getTotalCount();
}
