package com.qdtas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignManagerDto {

    @NotNull(message = "Project ID cannot be null")
    private Long projectId;

    @NotNull(message = "Employee ID cannot be null")
    private Long eId;

}
