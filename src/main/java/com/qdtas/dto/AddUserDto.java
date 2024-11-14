package com.qdtas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qdtas.utils.NoSpaces;
import com.qdtas.utils.NonZero;
import com.qdtas.utils.SubRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDto {

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Username cannot be blank")
    private String userName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NoSpaces(message = "Password Should not contain spaces")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,14}$", message = "Password should contain at least one digit, one special character and one lowercase and uppercase alphabate")
    private String password;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only alphabets are allowed")
    private String address;

    @NonZero
    @NotNull(message = "Department ID cannot be null")
    private Long deptId;

    @Pattern(regexp = "^[A-Z]*(_[A-Z]*)?$", message = "Only one underscore and Uppercase alphabates allowed")
    @NotBlank(message = "Role cannot be blank")
    private String role;

    @Pattern(regexp = "^[A-Z]*(_[A-Z]*)?$", message = "Only one underscore and Uppercase alphabates allowed")
    @NotBlank(message = "Sub Role cannot be blank")
    private SubRole subRole;

    @Pattern(regexp = "^[0-9]{10}$", message = "Please enter exactly 10 digits")
    private String phoneNumber;

    @NonZero
    @NotNull(message = "job ID cannot be null")
    private Long jobId;

    @NonZero
    @NotNull(message = "job Category ID cannot be null")
    private Long jobCategoryId;

    @NonZero
    @NotNull(message = "Employment Status ID cannot be null")
    private Long employmentStatusId;

    @NotNull(message = "Please provide a valid birthDate")
    @Past(message = "Birthdate must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

}
