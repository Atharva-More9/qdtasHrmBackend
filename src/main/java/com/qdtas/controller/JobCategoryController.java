package com.qdtas.controller;

import com.qdtas.entity.JobCategory;
import com.qdtas.service.JobCategoryService;
import io.swagger.v3.oas.annotations.Hidden;
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
@RequestMapping("/jobcategory")
@Tag(name = "9. JobCategory")
@CrossOrigin
public class JobCategoryController {

    @Autowired
    private JobCategoryService jobCategoryService;

    @Operation(
            description = "Employment Status creation ex.Officials and Managers, Professionals, etc.",
            summary = "1.Create new Job Category",
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
    @PostMapping("/addJobCategory")
    public ResponseEntity<?> addJobCategory(@Valid @RequestBody JobCategory jobCategory) {
        return new ResponseEntity<>(jobCategoryService.createJobCategory(jobCategory), HttpStatus.CREATED);
    }

    @GetMapping("/getJobById/{id}")
    public ResponseEntity<?> getJobCategoryById(@PathVariable long id) {
        return new ResponseEntity<>(jobCategoryService.getById(id), HttpStatus.OK);
    }

    @Operation(
            description = "Update Job Category",
            summary = "Update Job Category",
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
    @PostMapping("/updateJobById/{id}")
    public ResponseEntity<?> updateById(@PathVariable long id, @Valid @RequestBody JobCategory jobCategory) {
        return new ResponseEntity<>(jobCategoryService.updateById(id, jobCategory), HttpStatus.OK);
    }

    @Operation(
            description = "Delete Job Category",
            summary = "Delete Job Category",
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
    @PostMapping("/deleteJobById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return new ResponseEntity<>(jobCategoryService.deleteById(id), HttpStatus.OK);
    }

    @Operation(
            description = "Get All Job Categories",
            summary = "Get All Job Categories",
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
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllJobCategories(
            @RequestParam(value = "pgn", defaultValue = "1") int pgn,
            @RequestParam(value = "sz", defaultValue = "10") int size) {
        pgn = pgn < 0 ? 0 : pgn - 1;
        size = size < 0 ? 5 : size;
        List<JobCategory> ul = jobCategoryService.getAll(pgn, size);
        return new ResponseEntity<>(ul, HttpStatus.OK);
    }
}
