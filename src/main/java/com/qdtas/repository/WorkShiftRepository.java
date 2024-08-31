package com.qdtas.repository;

import com.qdtas.entity.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalTime;

public interface WorkShiftRepository extends JpaRepository<WorkShift, Long>, CrudRepository<WorkShift, Long> {


    @Query("UPDATE WorkShift ws SET ws.workShiftName = :workShiftName, ws.startTime = :startTime, ws.endTime = :endTime WHERE ws.workShiftId = :workShiftId")
    int updateWorkShiftDetails(long workShiftId, String workShiftName, LocalTime startTime, LocalTime endTime);
}
