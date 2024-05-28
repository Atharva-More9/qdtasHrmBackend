package com.qdtas.repository;

import com.qdtas.entity.Timesheet;
import com.qdtas.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeSheetRepository extends JpaRepository<Timesheet,Long> {


    @Query(value = "SELECT * FROM timesheet WHERE emp_id=:empId order by date desc ",nativeQuery = true)
    public List<Timesheet> findAllByEmp(long empId ,Pageable pageable);
}
