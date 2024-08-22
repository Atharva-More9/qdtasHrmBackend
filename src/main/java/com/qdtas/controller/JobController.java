package com.qdtas.controller;

import com.qdtas.entity.Job;
import com.qdtas.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Hidden;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/job")
@Tag(name = "7. Job")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;

    @Operation(
            description = "Job creation ex.Developer, QA, etc.",
            summary = "1.Create new Job",
            responses = {
                    @ApiResponse(
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
    public ResponseEntity<?> create(@Valid @RequestBody Job job) {
        return new ResponseEntity<>(jobService.create(job), HttpStatus.CREATED);
    }

    @GetMapping("/getById/{jobId}")
    public ResponseEntity<?> get(@PathVariable long jobId) {
        return new ResponseEntity<>(jobService.getById(jobId), HttpStatus.OK);
    }

    @Operation(
            description = "Update Job Name and Description",
            summary = "Update Job Name and Description",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @PostMapping("/updateById/{jobId}")
    public ResponseEntity<?> update(@PathVariable long jobId, @Valid @RequestBody Job jobName) {
        return new ResponseEntity<>(jobService.updateById(jobId, jobName), HttpStatus.OK);
    }

    @Operation(
            description = "Delete Job",
            summary = "Delete Job",
            responses = {
                    @ApiResponse(
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
    @PostMapping("/deleteById/{jobId}")
    public ResponseEntity<?> delete(@PathVariable long jobId) {
        return new ResponseEntity<>(jobService.deleteById(jobId), HttpStatus.OK);
    }

    @Operation(
            description = "Get All Department",
            summary = "Get All Department",
            responses = {
                    @ApiResponse(
                            description = "Successful",
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
    @GetMapping("/getAllJobs")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "pgn", defaultValue = "1") int pgn,
            @RequestParam(value = "sz", defaultValue = "10") int size) {
        pgn = pgn < 0 ? 0 : pgn - 1;
        size = size <= 0 ? 5 : size;
        List<Job> ul = jobService.getAll(pgn, size);
        return new ResponseEntity<>(ul, HttpStatus.OK);
    }

}
