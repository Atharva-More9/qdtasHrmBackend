package com.qdtas.controller;

import com.qdtas.entity.GeneralInformation;
import com.qdtas.service.GeneralInformationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generalinfo")
public class GeneralInformationController {

    @Autowired
    private GeneralInformationService generalInformationService;

    @PostMapping("/add")
    public ResponseEntity<GeneralInformation> addGeneralInformation(@Valid @RequestBody GeneralInformation generalInformation) {
        return new ResponseEntity<>(generalInformationService.addGeneralInformation(generalInformation), HttpStatus.CREATED);
    }

    @GetMapping("/getAllInfo")
    public ResponseEntity<?> getGeneralInformation() {
        List<GeneralInformation> allGeneralInfo = generalInformationService.getAllGeneralInformation();
        return new ResponseEntity<>(allGeneralInfo, HttpStatus.OK);
    }
}
