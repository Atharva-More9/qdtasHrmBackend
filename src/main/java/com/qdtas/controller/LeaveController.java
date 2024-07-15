package com.qdtas.controller;

import com.qdtas.dto.JsonMessage;
import com.qdtas.dto.LeaveDTO;
import com.qdtas.entity.Leave;
import com.qdtas.entity.User;
import com.qdtas.service.LeaveService;
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


@RestController
@RequestMapping("/leave")
@CrossOrigin
@Tag(name = "3. Leave")
public class LeaveController {
    @Autowired
    private LeaveService leaveRequestService;

    @Hidden
    @GetMapping("/getAllLeaves")
    public ResponseEntity<?> getAllLeaveRequests(@RequestParam(value = "pgn",defaultValue = "1") int pgn,
                                                 @RequestParam(value = "sz" ,defaultValue = "10") int size){
        pgn = pgn < 0 ? 0 : pgn-1;
        size = size <= 0 ? 10 : size;
        List<Leave> l = leaveRequestService.getAllLeaveRequests(pgn, size);
        return new ResponseEntity<>(l, HttpStatus.OK);

    }
    /*
        @Operation(
                description = "Create Leave Request",
                summary = "1. Create leave",
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
        )*/
    @Hidden
    @PostMapping("/create/{empId}")
    public ResponseEntity<?> createLeaveRequest(@Valid @RequestBody LeaveDTO leaveRequest,@PathVariable long empId) {
        return new ResponseEntity(leaveRequestService.createLeaveRequest(empId,leaveRequest), HttpStatus.CREATED);
    }
/*

    @Operation(
            description = "Update Leave Request",
            summary = "Update Leave",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @PostMapping("/update/{LeaveId}")
    public ResponseEntity<?> updateLeaveRequest(@PathVariable Long LeaveId, @RequestBody LeaveDTO updatedLeaveRequest) {
        return new ResponseEntity(leaveRequestService.updateLeaveRequest(LeaveId, updatedLeaveRequest), HttpStatus.OK);

    }
*/

    @Operation(
            description = "Delete Leave Request",
            summary = "Delete Leave",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @DeleteMapping("/delete/{leaveId}")
    public ResponseEntity<?> deleteLeaveRequest(@PathVariable Long leaveId) {
        leaveRequestService.deleteLeaveRequest(leaveId);
        return new ResponseEntity(new JsonMessage("Successfully Deleted"), HttpStatus.OK);
    }

    @Operation(
            description = "Approve Leave Request (ADMIN only)",
            summary = "Approve request (Admin)",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approve/{leaveId}")
    public ResponseEntity<?> approveLeaveRequest(@PathVariable Long leaveId) {
        return new ResponseEntity(leaveRequestService.approveLeaveRequest(leaveId), HttpStatus.OK);
    }

    @Operation(
            description = "Get Leave ",
            summary = "Reject request (Admin)",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @PostMapping("/reject/{leaveId}")
    public ResponseEntity<?> rejectLeaveRequest(@PathVariable Long leaveId) {
        return new ResponseEntity(leaveRequestService.rejectLeaveRequest(leaveId), HttpStatus.OK);
    }



    @Operation(
            description = "Get All the Leaves by Employee Id",
            summary = "Get All the Leaves by Employee Id",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content

                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
            }
    )
    @GetMapping("/getAll/{empId}")
    public ResponseEntity<?> getAllById(@PathVariable long empId){
        List<Leave> leaveByEmpId = leaveRequestService.getLeaveByEmpId(empId);
        return new ResponseEntity<>(leaveByEmpId , HttpStatus.OK);
    }
}
