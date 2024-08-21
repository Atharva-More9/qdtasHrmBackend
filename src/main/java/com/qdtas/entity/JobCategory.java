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
    @NotBlank(message = "Job name should not be blank")
    @Column(name = "job_category_name", length = 100, nullable = false)
    private String jobCategoryName;

    // Updated the field name from jobCategories to users for better clarity
    @OneToMany(mappedBy = "jobCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference  // To handle bidirectional relationship in JSON serialization
    private Set<User> users = new HashSet<>();
}
