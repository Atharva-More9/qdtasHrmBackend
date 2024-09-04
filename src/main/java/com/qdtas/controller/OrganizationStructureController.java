package com.qdtas.controller;


import com.qdtas.entity.OrganizationStructure;
import com.qdtas.service.OrganizationStructureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/organization")
@Tag(name = "12. OrganizationStructure")
@CrossOrigin
public class OrganizationStructureController {

    @Autowired
    private OrganizationStructureService organizationStructureService;

    @Operation(
            description = "CEO, CFO etc.",
            summary = "1.Create new Organization structure",
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
    public ResponseEntity<OrganizationStructure> addOrganizationStructure(@RequestBody OrganizationStructure organizationStructure) {
        return new ResponseEntity<>(organizationStructureService.addOrganizationStructure(organizationStructure), HttpStatus.CREATED);
    }

    @Operation(
            description = "Get All Organization Structure",
            summary = "Get All Organization Structure",
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
    @GetMapping("/getAllOrg")
    public ResponseEntity<List<OrganizationStructure>> getAllOrg() {
        List<OrganizationStructure> allOrg = organizationStructureService.getAllOrganizationStructure();
        return new ResponseEntity<>(allOrg, HttpStatus.OK);
    }

}
