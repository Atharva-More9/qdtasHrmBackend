package com.qdtas.service;

import com.qdtas.entity.LeaveCount;

public interface LeaveCountService {

    LeaveCount addLeaveCount(LeaveCount leaveCount);

    LeaveCount updateLeaveCount(long id, LeaveCount leaveCount);

    LeaveCount getLeaveCountByUserId(long userId);

    void adjustLeaveCount(long userId, int leaveDays, boolean isApproved);
}
