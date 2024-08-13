package com.qdtas.service.impl;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.EmploymentStatus;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.EmploymentStatusRepository;
import com.qdtas.service.EmploymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmploymentStatusServiceImpl implements EmploymentStatusService {

    @Autowired
    private EmploymentStatusRepository employmentStatusRepository;

    @Override
    public EmploymentStatus create(EmploymentStatus employmentStatus) {
        return employmentStatusRepository.save(employmentStatus);
    }

    @Override
    public EmploymentStatus updateById(Long id, EmploymentStatus employmentStatus) {
        EmploymentStatus oldEmploymentStatus = employmentStatusRepository.findById(id).get();
        oldEmploymentStatus.setEmploymentStatusId(employmentStatus.getEmploymentStatusId());
        return employmentStatusRepository.save(oldEmploymentStatus);
    }

    @Override
    public EmploymentStatus getById(Long id) {
        return employmentStatusRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("EmploymentStatus", "id", String.valueOf(id)));
    }

    @Override
    public JsonMessage deleteById(Long id) {
        try{
            employmentStatusRepository.deleteById(id);
            return new JsonMessage("EmploymentStatus deleted Successfully");
        }
        catch(Exception exe){
            return new JsonMessage("Something went wrong");
        }


    }

    @Override
    public List<EmploymentStatus> getAll(int pgn, int size) {
        return employmentStatusRepository.findAll(PageRequest.of(pgn, size, Sort.by(Sort.Direction.ASC, "employmentStatusName"))).stream().collect(Collectors.toList());
    }
}
