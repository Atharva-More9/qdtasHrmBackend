package com.qdtas.repository;

import com.qdtas.entity.LeaveCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LeaveCountRepository extends CrudRepository<LeaveCount, Long>, JpaRepository<LeaveCount, Long> {

    Optional<LeaveCount> findByUserId(Long Id); // Ensure the property name matches

}
