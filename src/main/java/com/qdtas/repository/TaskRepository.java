package com.qdtas.repository;

import com.qdtas.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query(value = "select * from task where emp_id =" +
            " %:empId% order by name asc", nativeQuery = true)
    public List<Task> getTasksByEmpId(long empId, Pageable pg);

    @Query(value = "select * from task where emp_id =" +
            "%:empId% AND status = %:status% order by name asc", nativeQuery = true)
    public List<Task> getTasksByStatusAndEmpId(long empId,String status,Pageable pg);
}
