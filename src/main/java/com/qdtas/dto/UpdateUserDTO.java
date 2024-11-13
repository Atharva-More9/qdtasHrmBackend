package com.qdtas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qdtas.entity.Department;
import com.qdtas.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class    UpdateUserDTO {

    private String userName;

    private String email;

    private String password;

    private String firstName;

    private String middleName;

    private String lastName;

    private String gender;

    private long deptId;

    private String role;

    private String subRole;

    private String phoneNumber;

    private String address;

    private long jobId;

    private long jobCategoryId;

    private long employmentStatusId;

    private Boolean emailVerified;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date joinDate;
}
