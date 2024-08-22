package com.qdtas.controller;

import com.qdtas.entity.EmploymentStatus;
import com.qdtas.service.EmploymentStatusService;
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
@RequestMapping("/employment")
@Tag(name = "8. EmploymentStatus")
@CrossOrigin
public class EmploymentStatusController {

    @Autowired
    private EmploymentStatusService employmentStatusService;

    @Operation(
            description = "Employment Status creation ex.Freelancer, Full-time, etc.",
            summary = "1.Create new Employment Status",
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
    public ResponseEntity<?> create(@Valid @RequestBody EmploymentStatus employmentStatus) {
        return new ResponseEntity<>(employmentStatusService.create(employmentStatus), HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        return new ResponseEntity<>(employmentStatusService.getById(id), HttpStatus.OK);
    }

    @Operation(
            description = "Update Employment Status",
            summary = "Update Employment Status",
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
    @PostMapping("/updateById/{id}")
    public ResponseEntity<?> updateById(@PathVariable long id, @Valid @RequestBody EmploymentStatus employmentStatus) {
        return new ResponseEntity<>(employmentStatusService.updateById(id, employmentStatus), HttpStatus.OK);
    }

    @Operation(
            description = "Delete Employment Status",
            summary = "Delete Employment Status",
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
    @PostMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return new ResponseEntity<>(employmentStatusService.deleteById(id), HttpStatus.OK);
    }

    @Operation(
            description = "Get All Employment Status",
            summary = "Get All Employment Status",
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
    public ResponseEntity<?> getAllJobs(
            @RequestParam(value = "pgn", defaultValue = "1") int pgn,
            @RequestParam(value = "sz", defaultValue = "10") int size) {
        pgn = pgn < 0 ? 0 : pgn - 1;
        size = size < 0 ? 5 : size;
        List<EmploymentStatus> ul = employmentStatusService.getAll(pgn, size);
        return new ResponseEntity<>(ul, HttpStatus.OK);
    }
}
