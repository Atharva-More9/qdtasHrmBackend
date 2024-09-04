package com.qdtas.service.impl;

import com.qdtas.entity.OrganizationStructure;
import com.qdtas.repository.OrganizationStructureRepository;
import com.qdtas.service.OrganizationStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationStructureServiceImpl implements OrganizationStructureService {


    @Autowired
    private OrganizationStructureRepository organizationStructureRepository;

    @Override
    public List<OrganizationStructure> getAllOrganizationStructure() {
        List<OrganizationStructure> org = organizationStructureRepository.findAll();
        return org;
    }

    @Override
    public OrganizationStructure addOrganizationStructure(OrganizationStructure organizationStructure) {
        return organizationStructureRepository.save(organizationStructure);
    }
}
