package com.qdtas.repository;

import com.qdtas.entity.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmploymentStatusRepository extends CrudRepository<EmploymentStatus, Long>, JpaRepository<EmploymentStatus, Long> {
}
