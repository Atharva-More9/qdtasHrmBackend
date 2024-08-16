package com.qdtas.repository;

import com.qdtas.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<Job, Long>, JpaRepository<Job, Long> {

}
