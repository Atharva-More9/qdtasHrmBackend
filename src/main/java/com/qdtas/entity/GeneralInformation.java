package com.qdtas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GeneralInformation")
public class GeneralInformation {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;

    private String organizationName;

    private int numberOfEmployees;

    private String registrationNumber;

    private Long taxId;

    private Long phoneNumber;

    private String email;

    private String address;

    private String city;

    private String state;

    private String country;

    private Long postalCode;


}
