package com.qdtas.controller;

import com.qdtas.entity.JobCategory;
import com.qdtas.entity.WorkShift;
import com.qdtas.service.WorkShiftService;
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
@RequestMapping("/workshift")
@Tag(name = "11. WorkShift")
@CrossOrigin
public class WorkShiftController {

    @Autowired
    private WorkShiftService wss;

    @Operation(
            description = "Work Shift Creation ex. DayShift, NightShift.",
            summary = "1.Create new Work Shift",
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
    public ResponseEntity<?> addWorkShift(@Valid @RequestBody WorkShift workShift) {
        return new ResponseEntity<>(wss.createWorkShift(workShift), HttpStatus.CREATED);
    }

    @Operation(
            description = "Get Work Shift By Id",
            summary = "2.Get Work Shift By Id",
            responses = {
                    @ApiResponse(
                            description = "Successful request",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getWorkShiftById(@PathVariable long id) {
        return new ResponseEntity<>(wss.getWorkShiftById(id), HttpStatus.OK);
    }

    @Operation(
            description = "Update Work Shift",
            summary = "Update Work Shift",
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
    public ResponseEntity<?> updateWorkShiftById(@PathVariable long id, @Valid @RequestBody WorkShift workShift) {
        return new ResponseEntity<>(wss.updateWorkShiftById(id, workShift), HttpStatus.OK);
    }

    @Operation(
            description = "Delete Work Shift",
            summary = "Delete Work Shift",
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
    public ResponseEntity<?> deleteWorkShiftById(@PathVariable long id) {
        return new ResponseEntity<>(wss.deleteWorkShiftById(id), HttpStatus.OK);
    }

    @Operation(
            description = "Get All Work Shifts",
            summary = "Get All Work Shifts",
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
    public ResponseEntity<?> getAllWorkShifts(
            @RequestParam(value = "pgn", defaultValue = "1") int pgn,
            @RequestParam(value = "sz", defaultValue = "10") int size) {
        pgn = pgn < 0 ? 0 : pgn - 1;
        size = size < 0 ? 5 : size;
        List<WorkShift> ul = wss.getAllWorkShifts(pgn, size);
        return new ResponseEntity<>(ul, HttpStatus.OK);
    }
}
