package com.qdtas.repository;

import com.qdtas.entity.WorkShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface WorkShiftRepository extends JpaRepository<WorkShift, Long>, CrudRepository<WorkShift, Long> {
}
