package com.qdtas.repository;

import com.qdtas.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query(value = "select * from project where project_name" +
            " like %:keyword% order by project_name asc", nativeQuery = true)
    List<Project> findProjectByName(String keyword, Pageable pg);
}
