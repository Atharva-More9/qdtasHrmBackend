package com.qdtas.service;

import com.qdtas.dto.JsonMessage;
import com.qdtas.dto.UpdateRecruitmentDto;
import com.qdtas.entity.Recruitment;

import java.util.List;

public interface RecruitmentService {

    public Recruitment create(Recruitment recruitment);

    public Recruitment getById(long recruitmentId);

    public List<Recruitment> getAllRecruitments(int pgn, int size);

    public Recruitment updateById(long Id, UpdateRecruitmentDto ur);

    public JsonMessage deleteById(long recruitmentId);
}
