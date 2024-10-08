package com.qdtas.controller;

import com.qdtas.dto.JsonMessage;
import com.qdtas.entity.Timesheet;
import com.qdtas.service.TimesheetService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ts")
@Tag(name = "5. Timesheet")
@CrossOrigin
public class TimesheetController {

    @Autowired
    private TimesheetService tsr;


    @ApiOperation(value = "Add Timesheet", position = 1)
    @Operation(
            description = "Add Timesheet",
            summary = "1. Add Timesheet",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Something Went Wrong",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody Timesheet ts){
        return new ResponseEntity<>(tsr.addTimesheet(ts), HttpStatus.CREATED);
    }

   @Hidden
    @PostMapping("/getById/{tId}")
    public ResponseEntity<?> getById(@PathVariable long tId){
        return new ResponseEntity<>(tsr.getTimesheetById(tId),HttpStatus.OK);
    }

    @ApiOperation(value = "Get All Timesheets By Employee Id", position = 2)
    @Operation(
            description = "Get All Timesheets By Employee Id",
            summary = "3.Get All Timesheets By Employee Id",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Something Went Wrong",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @GetMapping("/getByEmpId/{eId}")
    public ResponseEntity<?> getByEmp(@RequestParam(value = "pgn",defaultValue = "1") int pgn,
                                      @RequestParam(value = "sz" ,defaultValue = "30") int size,
                                      @PathVariable int eId){
        pgn = pgn < 0 ? 0 : pgn-1;
        size = size <= 0 ? 30 : size;
        return new ResponseEntity<>(tsr.getTimesheetByEmployeeId(eId,pgn,size),HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Timesheet By  Id", position = 1)
    @Operation(
            description = "Delete Timesheet By  Id",
            summary = "4.Delete Timesheet By  Id",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Timesheet Not Found",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/deleteById/{tId}")
    public ResponseEntity<?> deleteById(@PathVariable long tId){
        try {
            tsr.deleteById(tId);
            return new ResponseEntity<>(new JsonMessage("Timesheet Deleted Successfully"),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new JsonMessage("Timesheet Not Found"),HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Update Timesheets By  Id", position = 1)
    @Operation(
            description = "Update Timesheets By  Id",
            summary = "2.Update Timesheets By  Id",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Something Went Wrong",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Only a creator of Timesheet can update timesheet",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/updateById/{tsId}")
    public ResponseEntity<?> updateById( @PathVariable long tsId ,@Valid @RequestBody Timesheet ts){
        return new ResponseEntity<>(tsr.updateTimesheet(tsId,ts), HttpStatus.OK);
    }

    @Operation(
            description = "Get Total number of timesheets",
            summary = "Get Total number of timesheets",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Timesheet Not Found",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @GetMapping("/getTotalCount/{tsId}")
    public ResponseEntity<?> getTotalCountById(@PathVariable long tsId){
        int timesheets = tsr.getTotalCountById(tsId);
        return new ResponseEntity<>(timesheets, HttpStatus.OK);
    }

}
