package com.qdtas.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long jobId;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Job name should not be blank")
    private String jobName;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Job Description should not be blank")
    private String jobDescription;

    @JsonIgnore
    @OneToMany(mappedBy = "jobId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> jobs;

}
