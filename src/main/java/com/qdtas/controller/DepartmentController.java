package com.qdtas.controller;

import com.qdtas.entity.Department;
import com.qdtas.entity.User;
import com.qdtas.service.DepartmentService;
import io.swagger.annotations.Api;
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
@RequestMapping("/dept")
@Tag(name = "2. Department")
@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService dsr;

    @Operation(
            description = "Department creation ex.DEV,QA,etc",
            summary = "1. Create new department",
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
    @PostMapping("/add")
    public ResponseEntity<?> create(@Valid @RequestBody Department dt) {
        return new ResponseEntity<>(dsr.create(dt), HttpStatus.CREATED);
    }

    @Hidden
    @GetMapping("/get/{deptId}")
    public ResponseEntity<?> getById(@PathVariable long deptId) {
        return new ResponseEntity<>(dsr.getById(deptId), HttpStatus.OK);
    }

    @Operation(
            description = "Department Name Update",
            summary = "Update Department Name",
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
    @PostMapping("/update/{deptId}")
    public ResponseEntity<?> update(@PathVariable("deptId") long deptId, @RequestBody Department deptName) {
        return new ResponseEntity<>(dsr.updateById(deptId,deptName), HttpStatus.OK);
    }

    @Operation(
            description = "Delete Department",
            summary = "Delete Department",
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
    @PostMapping("/delete/{deptId}")
    public ResponseEntity<?> delete(@PathVariable long deptId) {
        return new ResponseEntity<>(dsr.deleteById(deptId), HttpStatus.OK);
    }

    @Operation(
            description = "Get All Department",
            summary = "Get All Department",
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
    @GetMapping("/getAllDepartments")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "pgn", defaultValue = "1") int pgn,
            @RequestParam(value = "sz", defaultValue = "10") int size) {
        pgn = pgn < 0 ? 0 : pgn - 1;
        size = size <= 0 ? 5 : size;
        List<Department> ul = dsr.getAllDepartments(pgn, size);
        return new ResponseEntity<>(ul, HttpStatus.OK);

    }
 
    @Operation(
            description = "Department Name Update",
            summary = "Update Department Name",
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
    @PostMapping("/getAllEmpByDept/{deptId}")
    public ResponseEntity<?> getAllUsers(@PathVariable long deptId) {
        return new ResponseEntity<>(dsr.getAllUsers(deptId), HttpStatus.OK);
    }

    @Operation(
            description = "Get Total number of Departments",
            summary = "Get Total number of Departments",
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
    @GetMapping("/getTotalCount")
    public ResponseEntity<?> getTotalCount(){
        return new ResponseEntity<>(dsr.getTotalCount(), HttpStatus.OK);
    }
}
