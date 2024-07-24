package com.qdtas.repository;

import com.qdtas.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Query(value = "SELECT * FROM leave_request WHERE user_id= " +
            " %:empId% ORDER BY start_date desc ", nativeQuery = true)

    List<Leave> findAllByEmployeeId(long empId);

    List<Leave> findByEmployeeIdAndStatus(long empId, String name);
}
