package com.qdtas.repository;

import com.qdtas.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Query(value = "SELECT * FROM leave_request WHERE user_id = :empId ORDER BY start_date DESC", nativeQuery = true)
    List<Leave> findAllByEmployeeId(@Param("empId") long empId);

    @Query(value = "SELECT * FROM leave_request WHERE user_id = :empId AND status = 'APPROVED' AND start_date <= :endDate AND end_date >= :startDate", nativeQuery = true)
    List<Leave> findApprovedLeavesByEmployeeAndDateRange(@Param("empId") long empId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
