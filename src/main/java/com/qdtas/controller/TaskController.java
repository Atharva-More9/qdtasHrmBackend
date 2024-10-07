package com.qdtas.controller;

import com.qdtas.dto.JsonMessage;
import com.qdtas.dto.TaskDto;
import com.qdtas.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/task")
@CrossOrigin
@Tag(name = "6. Task")
public class TaskController {

    @Autowired
    private TaskService tsr;

    @ApiOperation(value = "Assign Task to employee", position = 1)
    @Operation(
            description = "assign Task",
            summary = "1. assign Task",
            responses = {
                    @ApiResponse(
                            description = "Assigned",
                            responseCode = "201",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/assign")
    public ResponseEntity<?> assign(@Valid @RequestBody TaskDto td){
        try{
            return new ResponseEntity<>(tsr.assignTask(td), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(new JsonMessage("Something Went Wrong."), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get All Tasks By Employee", position = 1)
    @Operation(
            description = "get Tasks",
            summary = "get Tasks",
            responses = {
                    @ApiResponse(
                            description = "ok",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @GetMapping("/getTaskByEmpId/{eId}")
    public ResponseEntity<?> getTaskByEmpId(@PathVariable long eId,
                                            @RequestParam(value = "pgn",defaultValue = "1") int pgn,
                                            @RequestParam(value = "sz" ,defaultValue = "5") int sz){
        pgn = pgn < 0 ? 0 : pgn-1;
        sz = sz <= 0 ? 5 : sz;
        return new ResponseEntity<>(tsr.getTaskByEmpId(eId,pgn,sz),HttpStatus.OK);
    }

    @ApiOperation(value = "Get All Tasks By Employee and Status", position = 1)
    @Operation(
            description = "get Tasks by status",
            summary = "get Tasks by empid and status",
            responses = {
                    @ApiResponse(
                            description = "ok",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @GetMapping("/getTaskByStatus/{eId}")
    public ResponseEntity<?> getTaskByStatus(@PathVariable long eId,
                                             @RequestParam("st") String st,
                                             @RequestParam(value = "pgn",defaultValue = "1") int pgn,
                                             @RequestParam(value = "sz" ,defaultValue = "5") int size){
        pgn = pgn < 0 ? 0 : pgn-1;
        size = size <= 0 ? 5 : size;
        return new ResponseEntity<>(tsr.getTaskByStatus(eId,st,pgn,size),HttpStatus.OK);

    }

    @ApiOperation(value = "Change a tasks status to Completed", position = 1)
    @Operation(
            description = "Change a tasks status to Completed",
            summary = "3. Change a tasks status to Completed",
            responses = {
                    @ApiResponse(
                            description = "Task Completed.",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/completed/{tId}")
    public ResponseEntity<?> completed(@PathVariable long tId){
        try{
            tsr.completeTask(tId);
            return new ResponseEntity<>(new JsonMessage("Task Completed"), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(new JsonMessage("Something Went Wrong."), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Change a tasks status to Under_Review", position = 1)
    @Operation(
            description = "Change a tasks status to Under_Review",
            summary = "2. Change a tasks status to Under_Review",
            responses = {
                    @ApiResponse(
                            description = "Task Under_Review.",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @PostMapping("/review/{tId}")
    public ResponseEntity<?> review(@PathVariable long tId){
        try{
            tsr.reviewTask(tId);
            return new ResponseEntity<>(new JsonMessage("Task Under Review."), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(new JsonMessage("Something Went Wrong."), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            description = "Get Total number of Tasks",
            summary = "Get Total number of Tasks",
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
    @GetMapping("/getTotalCount")
    public ResponseEntity<?> getTotalCount(){
        int tasks = tsr.getTotalCount();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
