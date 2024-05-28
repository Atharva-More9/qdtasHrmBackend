package com.qdtas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    @NotBlank(message = "Project Name Cannot be Blank")
    @NotNull(message = "Project Name Cannot be Blank")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only alphabets are allowed")
    private String projectName;

    @NotBlank(message = "Project Description Cannot be Blank")
    @NotNull(message = "Project Description Cannot be Blank")
    private  String description;

    @NotBlank(message = "Project Status Cannot be Blank")
    @NotNull(message = "Project Status Cannot be Blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    private String status;

    @NotBlank(message = "Project Type Cannot be Blank")
    @NotNull(message = "Project Type Cannot be Blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    private String type;

    @NotBlank(message = "Client Cannot be Blank")
    @NotNull(message = "Client Cannot be Blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    private String client;

}
