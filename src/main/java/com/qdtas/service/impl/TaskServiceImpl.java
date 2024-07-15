package com.qdtas.service.impl;

import com.qdtas.dto.TaskDto;
import com.qdtas.entity.Task;
import com.qdtas.entity.User;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.TaskRepository;
import com.qdtas.service.TaskService;
import com.qdtas.service.UserService;
import com.qdtas.utils.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository trp;

    @Autowired
    private UserService usr;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task assignTask(TaskDto td) {
        Task t= new Task();
        t.setName(td.getName());
        t.setDescription(td.getDescription());
        t.setDueDate(td.getDueDate());
        t.setAssignee(td.getAssignee());
        t.setStatus(TaskStatus.PENDING.name());
        User u = usr.getById(td.getEmpId());
        t.setEmpId(u);
        return trp.save(t);
    }

    @Override
    public List<Task> getTaskByEmpId(long eId,int pgn,int sz) {
        return trp.getTasksByEmpId(eId, PageRequest.of(pgn,sz, Sort.by("name")));
    }

    @Override
    public Task getTaskById(long tId) {
        return trp.findById(tId).orElseThrow(()->new ResourceNotFoundException("Task","TaskId",String.valueOf(tId)));
    }

    @Override
    public List<Task> getTaskByStatus(long eId, String status,int pgn,int sz) {
        return trp.getTasksByStatusAndEmpId(eId,status,PageRequest.of(pgn,sz, Sort.by("name")));
    }

    @Override
    public void deleteTask(long tId) {
        Task t = getTaskById(tId);
        trp.delete(t);
    }

    @Override
    public void completeTask(long tId) {
        Task t = trp.findById(tId).orElseThrow(()-> new ResourceNotFoundException("Task","TaskID",String.valueOf(tId)));
        t.setStatus(TaskStatus.COMPLETED.name());
        trp.save(t);
    }

    @Override
    public void reviewTask(long tId) {
        Task t = trp.findById(tId).orElseThrow(()-> new ResourceNotFoundException("Task","TaskID",String.valueOf(tId)));
        t.setStatus(TaskStatus.UNDER_REVIEW.name());
        trp.save(t);
    }

    @Override
    public List<Task> getTasksByStatusAndDateRange(long empId, String status, LocalDate startDate, LocalDate endDate) {
        return taskRepository.findTasksByStatusAndEmpIdAndDateRange(empId, status, startDate, endDate);
    }
}
