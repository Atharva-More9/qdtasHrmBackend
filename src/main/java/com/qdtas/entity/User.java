package com.qdtas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userId;

    private String userName;

    private String email;

    @JsonIgnore
    private String password;

    private String firstName;

    private String middleName;

    private String lastName;

    private String gender;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "dept_id")
    private Department dept;

    @Column(length = 32, nullable = false)
    private String role;

    private String phoneNumber;

    @Column(length = 150)
    private String address;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "job_id")
    private Job jobId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "employee_status_id")
    private EmploymentStatus employmentStatusId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "job_category_id")
    private JobCategory jobCategoryId;

    private int totalLeaves; // Default value for all new users

    private Boolean emailVerified;

    @JsonBackReference
    @ManyToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private Set<Project> projects = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date joinDate;

    // Set default value of totalLeaves to 20
    @PrePersist
    protected void onCreate() {
        if (this.totalLeaves == 0) {
            this.totalLeaves = 20;
        }
    }
}
