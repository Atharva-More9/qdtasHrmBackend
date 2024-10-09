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
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deptId;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Department name should not be blank")
    @Column(length = 20, nullable = false)
    private String deptName;

    @JsonIgnore
    @OneToMany(mappedBy = "dept", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<User> employees;

}
