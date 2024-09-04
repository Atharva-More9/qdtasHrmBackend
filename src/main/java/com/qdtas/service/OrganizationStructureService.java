package com.qdtas.service;

import com.qdtas.entity.OrganizationStructure;

import java.util.List;

public interface OrganizationStructureService {

    public List<OrganizationStructure> getAllOrganizationStructure();

    public OrganizationStructure addOrganizationStructure(OrganizationStructure organizationStructure);
}
