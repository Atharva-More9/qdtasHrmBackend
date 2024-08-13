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
public class JobCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int JobCategoryId;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Job name should not be blank")
    private String JobCategoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "employmentStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> JobCategories;
}
