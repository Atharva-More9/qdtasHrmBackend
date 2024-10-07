package com.qdtas.service;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.WorkShift;

import java.time.LocalTime;
import java.util.List;

public interface WorkShiftService {

    public WorkShift createWorkShift(WorkShift workShift);

    public WorkShift getWorkShiftById(long id);

    public WorkShift updateWorkShiftById(long workShiftId, String workShiftName, LocalTime startTime, LocalTime endTime);

    public JsonMessage deleteWorkShiftById(long id);

    public List<WorkShift> getAllWorkShifts(int pgn, int size);

    public int getTotalCount();
}
