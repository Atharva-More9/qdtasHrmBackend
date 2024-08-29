package com.qdtas.service.impl;

import com.qdtas.entity.GeneralInformation;
import com.qdtas.repository.GeneralInformationRepositry;
import com.qdtas.service.GeneralInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralInformationServiceImpl implements GeneralInformationService {

    @Autowired
    private GeneralInformationRepositry generalInformationRepositry;

    @Override
    public List<GeneralInformation> getAllGeneralInformation() {
       List <GeneralInformation> generalInformation = generalInformationRepositry.findAll();
        return generalInformation;
    }

    @Override
    public GeneralInformation addGeneralInformation(GeneralInformation generalInformation) {
        return generalInformationRepositry.save(generalInformation);
    }
}
