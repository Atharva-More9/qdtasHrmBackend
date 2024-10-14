package com.qdtas.controller;

import com.qdtas.entity.LeaveCount;
import com.qdtas.service.LeaveCountService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/leavecount")
@CrossOrigin
public class LeaveCountController {

    @Autowired
    private LeaveCountService leaveCountService;

    @Hidden
    @PostMapping("/add")
    public ResponseEntity<LeaveCount> addLeave(@RequestBody LeaveCount leaveCount) {
        return new ResponseEntity<>(leaveCountService.addLeaveCount(leaveCount), HttpStatus.CREATED);
    }

    @Hidden
    @PostMapping("/update")
    public ResponseEntity<LeaveCount> updateLeave(@RequestParam long id, @RequestBody LeaveCount leaveCount) {
        return new ResponseEntity<>(leaveCountService.updateLeaveCount(id, leaveCount), HttpStatus.OK);
    }

    @Hidden
    @GetMapping("/total/{Id}")
    public ResponseEntity<LeaveCount> getLeaveCountByUserId(@PathVariable long Id) {
        return new ResponseEntity<>(leaveCountService.getLeaveCountByUserId(Id), HttpStatus.OK);
    }

    @Hidden
    @PostMapping("/adjust/{Id}")
    public ResponseEntity<?> adjustLeaveCount(@PathVariable long Id, @RequestParam int leaveDays, @RequestParam boolean isApproved) {
        leaveCountService.adjustLeaveCount(Id, leaveDays, isApproved);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
