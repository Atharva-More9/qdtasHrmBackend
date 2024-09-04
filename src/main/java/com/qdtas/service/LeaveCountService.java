package com.qdtas.service;

import com.qdtas.entity.LeaveCount;

public interface LeaveCountService {

    public LeaveCount addLeaveCount(LeaveCount leaveCount);

    public LeaveCount updateLeaveCount(long id, LeaveCount leaveCount);

    public LeaveCount getAllLeaveCount();

    public LeaveCount getCasualLeaveCount();

    public LeaveCount getSickLeaveCount();
}
