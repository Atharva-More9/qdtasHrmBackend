package com.qdtas.service.impl;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.WorkShift;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.WorkShiftRepository;
import com.qdtas.service.WorkShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkShiftServiceImpl implements WorkShiftService {

    @Autowired
    private WorkShiftRepository wsr;

    @Override
    public WorkShift createWorkShift(WorkShift workShift) {
        return wsr.save(workShift);
    }

    @Override
    public WorkShift getWorkShiftById(long id) {
        return wsr.findById(id).orElseThrow(()->new ResourceNotFoundException("WorkShift", "id", String.valueOf(id)));
    }

    @Override
    public WorkShift updateWorkShiftById(long id, String workShiftName, LocalTime startTime, LocalTime endTime) {
        WorkShift w = wsr.findById(id).orElseThrow(() -> new ResourceNotFoundException("WorkShift", "id", String.valueOf(id)));
        w.setWorkShiftName(workShiftName);
        w.setStartTime(startTime);
        w.setEndTime(endTime);

        return wsr.save(w);
    }

    @Override
    public JsonMessage deleteWorkShiftById(long id) {
        try{
            wsr.deleteById(id);
            return new JsonMessage("WorkShift deleted successfully");
        }
        catch(Exception e){
            return new JsonMessage("Something Went Wrong");
        }
    }

    @Override
    public List<WorkShift> getAllWorkShifts(int pgn, int size) {
        return wsr.findAll(PageRequest.of(pgn, size, Sort.by(Sort.Direction.ASC, "workShiftName"))).stream().collect(Collectors.toList());
    }

    @Override
    public int getTotalCount() {
        return (int)wsr.count();
    }
}
