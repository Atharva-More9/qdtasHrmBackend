package com.qdtas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class JobCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobCategoryId;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Job Description should not be blank")
    private String jobCategoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "jobCategoryId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Set<User> jobCategoryUsers;
}
