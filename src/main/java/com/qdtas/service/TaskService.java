package com.qdtas.service;

import com.qdtas.dto.TaskDto;
import com.qdtas.entity.Task;
import com.qdtas.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface TaskService {

    public Task assignTask(TaskDto ts);

    public List<Task> getTaskByEmpId(long eId,int pgn,int sz);

    public Task getTaskById(long eId);

    public List<Task> getTaskByStatus(long eId,String status,int pgn,int sz);

    public void deleteTask(long tId);

    public void completeTask(long tId);

    public void reviewTask(long tId);

}
