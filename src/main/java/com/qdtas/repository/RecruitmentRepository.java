package com.qdtas.repository;

import com.qdtas.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RecruitmentRepository extends CrudRepository<Recruitment, Long> , JpaRepository<Recruitment, Long> {
//    Recruitment findById(long recruitmentId);
}
