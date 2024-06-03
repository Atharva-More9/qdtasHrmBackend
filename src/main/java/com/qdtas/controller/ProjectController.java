package com.qdtas.controller;

import com.qdtas.dto.JsonMessage;
import com.qdtas.dto.ProjectDTO;
import com.qdtas.entity.Project;
import com.qdtas.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@CrossOrigin
@Tag(name = "4. Project")
public class ProjectController {

    @Autowired
    private ProjectService psr;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Add Project

    @ApiOperation(value = "Add Project", position = 1)
    @Operation(
            description = "Add project",
            summary = "1.Add Project",
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
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectDTO pd) {
        try {
            return new ResponseEntity(psr.addProject(pd), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(new JsonMessage("Something Went Wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Update Project

    @ApiOperation(value = "Update Project", position = 1)
    @Operation(
            description = "Update project",
            summary = "2.Update Project",
            responses = {
                    @ApiResponse(
                            description = "Updated(OK)",
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
    @PutMapping("/update/{pId}")
    public ResponseEntity<?> updateProject(@Valid @RequestBody ProjectDTO pd, @PathVariable long pId) {
        try {
            return new ResponseEntity(psr.updateProject(pd, pId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new JsonMessage("Something Went Wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Search Project By Name

    @ApiOperation(value = "Search Project by Name", position = 1)
    @Operation(
            description = "Search project",
            summary = "3.Search Project",
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
                    )
            }
    )
    @PostMapping("/searchProject")
    public ResponseEntity<?> searchByName(@RequestParam("key") String keyword,
                                          @RequestParam(value = "pgn", defaultValue = "1") int pgn,
                                          @RequestParam(value = "sz", defaultValue = "10") int size) {
        pgn = pgn < 0 ? 0 : pgn - 1;
        size = size <= 0 ? 5 : size;
        return new ResponseEntity(psr.searchProjectByName(keyword, pgn, size), HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Assign Employee

    @ApiOperation(value = "Assign employee", position = 1)
    @Operation(
            description = "assign employee to project",
            summary = "4.Assign Employee To Project",
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
                    )
            }
    )
    @PostMapping("/assignEmployee")
    public ResponseEntity<?> assignEmp(@RequestParam(value = "empId", required = true) long empId, @RequestParam(value = "pId", required = true) long pId) {
        return new ResponseEntity<>(psr.assignEmployee(empId, pId), HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Assign multiple Employees

    @ApiOperation(value = "Assign multiple employees", position = 1)
    @Operation(
            description = "assign multile employees to project",
            summary = "5.Assign multiple Employees To Project",
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
                    )
            }
    )
    @PostMapping("/assignAll")
    public ResponseEntity<?> assignAll(@RequestParam("empIds") List<Long> empIds, @RequestParam(value = "pId", required = true) long pId) {
        return new ResponseEntity<>(psr.assignEmployees(empIds, pId), HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Get All Projects

    @ApiOperation(value = "get all projects", position = 1)
    @Operation(
            description = "get all projects",
            summary = "6.Get All Projects",
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
                    )
            }
    )
    @GetMapping("/getAllProjects")
    public ResponseEntity<?> getAll(@RequestParam(value = "pgn", defaultValue = "1") int pgn,
                                    @RequestParam(value = "sz", defaultValue = "10") int size) {
        pgn = pgn < 0 ? 0 : pgn - 1;
        size = size <= 0 ? 5 : size;
        return new ResponseEntity<>(psr.getAllProjects(pgn, size), HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Get Project By Id

    @ApiOperation(value = "Get Project by ID", position = 1)
    @Operation(
            description = "Get project by ID",
            summary = "9. Get Project by ID",
            responses = {
                    @ApiResponse(
                            description = "OK",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @GetMapping("/getProjectById/{pId}")
    public ResponseEntity<?> getProjectById(@PathVariable long pId) {
        try {
            Project project = psr.getProjectById(pId);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new JsonMessage("Project not found"), HttpStatus.NOT_FOUND);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Remove Employee From Project

    @ApiOperation(value = "Remove employee from project", position = 1)
    @Operation(
            description = "Remove employee from project",
            summary = "7.Remove employee from project",
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
                    )
            }
    )

    @PostMapping("/removeEmployee")
    public ResponseEntity<?> remove(@RequestParam(value = "empId", required = true) long empId, @RequestParam(value = "pId", required = true) long pId) {
        return new ResponseEntity<>(psr.removeEmployee(empId, pId), HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Remove Multiple Employees from Project

    @ApiOperation(value = "Remove multiple employees from project", position = 1)
    @Operation(
            description = "Remove multiple employees from project",
            summary = "8.Remove multiples employee from project",
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
                    )
            }
    )
    @PostMapping("/removeAll")
    public ResponseEntity<?> removeAll(@RequestParam("empIds") List<Long> empIds, @RequestParam(value = "pId", required = true) long pId) {
        return new ResponseEntity<>(psr.removeAll(empIds, pId), HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Assign Manager to Project

    @ApiOperation(value = "Assigns Manager to the Project", position = 1)
    @Operation(
            description = "Assigns Manager to the Project",
            summary = "9.Assigns Manager to the Project",
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
                    )
            }
    )
    @PostMapping("/assignManager")
    public ResponseEntity<?> assignManager(@RequestParam( value = "pId", required = true) long pId, @RequestParam(value = "eId", required = true) long eId) {
        Project project = psr.assignManager(eId, pId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Assign Multiple Managers to Project

    @ApiOperation(value = "Assigns Multiple Managers to the Project", position = 1)
    @Operation(
            description = "Assigns Multiple Managers to the Project",
            summary = "10.Assigns Multiple Managers to the Project",
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
                    )
            }
    )

    @PostMapping("/assignManagers")
    public ResponseEntity<?> assignManagers(@RequestParam(value ="pId", required = true) long pId , @RequestParam(value = "managerIds" , required = true) List<Long> managerIds){
        Project project = psr.assignManagers(managerIds, pId);
        return new ResponseEntity<>(project , HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Remove Manager From Project

    @ApiOperation(value = "Remove Manager from the Project", position = 1)
    @Operation(
            description = "Remove Manager from the Project",
            summary = "11.Remove Manager from the Project",
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
                    )
            }
    )

    @DeleteMapping("/removeManager")
    public ResponseEntity<?> removeManager(@RequestParam(value = "pId" , required = true) long pId , @RequestParam(value = "empId" , required = true) long empId){
        Project project = psr.removeManager(empId, pId);
        return new ResponseEntity<>(project , HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Remove Multiple Managers from Project

    @ApiOperation(value = "Remove Multiple Managers from the Project", position = 1)
    @Operation(
            description = "Remove Multiple Managers from the Project",
            summary = "12.Remove Multiple Managers from the Project",
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
                    )
            }
    )

    @DeleteMapping("/removeManagers")
    public ResponseEntity<?> removeManagers(@RequestParam(value = "pId" , required = true) long pID , @RequestParam(value = "empIds" , required = true) List<Long> empIds){
        Project project = psr.removeManagers(empIds, pID);
        return new ResponseEntity<>(project , HttpStatus.OK);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ApiOperation(value = "Delete Project", position = 1)
    @Operation(
            description = "Delete project by ID",
            summary = "13.Delete Project",
            responses = {
                    @ApiResponse(
                            description = "Successful deletion",
                            responseCode = "200",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500",
                            content = @io.swagger.v3.oas.annotations.media.Content
                    )
            }
    )
    @DeleteMapping("/delete/{pId}")
    public ResponseEntity<?> deleteProject(@PathVariable long pId) {
        try {
            psr.deleteProject(pId);
            return new ResponseEntity<>(new JsonMessage("Project deleted successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new JsonMessage("Something went wrong while deleting the project"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
