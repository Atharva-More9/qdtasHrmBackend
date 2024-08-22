package com.qdtas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class EmploymentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employmentStatusId;

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Job name should not be blank")
    @Column(name = "employment_status_name", length = 100, nullable = false)
    private String employmentStatusName;

    @OneToMany(mappedBy = "employmentStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference  // To handle bidirectional relationship in JSON serialization
    private Set<User> user;
}
