package com.qdtas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OrgainzationStructure")

public class OrganizationStructure {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String ceo;

    private String management;

    private String developer;

    private String tester;

    private String uiDesign;
}
