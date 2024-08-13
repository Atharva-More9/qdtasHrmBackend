package com.qdtas.repository;

import com.qdtas.entity.Job;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;

public interface JobRepository extends CrudRepository<Job, Long>, JpaRepository<Job, Long> {

}
