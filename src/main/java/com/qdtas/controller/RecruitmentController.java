package com.qdtas.controller;

import com.qdtas.dto.AddRecruitmentDto;
import com.qdtas.dto.JsonMessage;
import com.qdtas.dto.UpdateRecruitmentDto;
import com.qdtas.entity.Recruitment;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.service.RecruitmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/recruitment")
@CrossOrigin
@Tag(name = "14. Recruitment")
public class RecruitmentController {

    @Autowired
    private RecruitmentService recruitmentService;

    @Operation(
            description = "Recruitment creation etc",
            summary = "1. Create new Recruitment",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @PostMapping("/add")
    public ResponseEntity<?> addRecruitment(@Valid @RequestBody AddRecruitmentDto recruitment) {
        Recruitment newRecruitment = recruitmentService.create(recruitment);
        return new ResponseEntity<>(newRecruitment, HttpStatus.CREATED);
    }

    @Operation(
            description = "Recruitment Get By Id",
            summary = "Get Recruitment",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @GetMapping("/getById/{recruitmentId}")
    public ResponseEntity<?> getRecruitmentById(@PathVariable("recruitmentId") Long recruitmentId) {
        try {
            Recruitment recruitment = recruitmentService.getById(recruitmentId);
            return new ResponseEntity<>(recruitment, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new JsonMessage("Recruitment Not Found"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            description = "Recruitment Update",
            summary = "Update Recruitment",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @PostMapping("/update/{recruitmentId}")
    public ResponseEntity<?> updateRecruitment(@PathVariable("recruitmentId") long recruitmentId,
                                               @RequestBody UpdateRecruitmentDto recruitmentUpdateDto) {
        try {
            Recruitment updatedRecruitment = recruitmentService.updateById(recruitmentId, recruitmentUpdateDto);
            return new ResponseEntity<>(updatedRecruitment, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new JsonMessage("Recruitment Not Found"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            description = "Delete Recruitment",
            summary = "Delete Recruitment",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Successfull",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Something went wrong",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @DeleteMapping("/delete/{recruitmentId}")
    public ResponseEntity<?> deleteRecruitment(@PathVariable("recruitmentId") Long recruitmentId) {
        try {
            recruitmentService.deleteById(recruitmentId);
            return new ResponseEntity<>(new JsonMessage("Recruitment Deleted Successfully"), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new JsonMessage("Recruitment Not Found"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            description = "Get All Recruitments",
            summary = "Get All Recruitments",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Successfull",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @ApiResponse(
                            description = "Something went wrong",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @GetMapping("/all")
    public ResponseEntity<?> getAllRecruitments(@RequestParam(value = "pgn", defaultValue = "1") int pgn,
                                                @RequestParam(value = "sz", defaultValue = "10") int size) {
        List<Recruitment> recruitments = recruitmentService.getAllRecruitments(pgn - 1, size);
        return new ResponseEntity<>(recruitments, HttpStatus.OK);
    }
}
