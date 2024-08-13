package com.qdtas.repository;

import com.qdtas.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface JobCategoryRepository extends CrudRepository<JobCategory, Long>, JpaRepository<JobCategory, Long> {
}
