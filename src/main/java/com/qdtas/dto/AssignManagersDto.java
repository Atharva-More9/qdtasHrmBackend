package com.qdtas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignManagersDto {

    @NotNull(message = "Project ID cannot be null")
    private Long projectId;

    @NotNull(message = "Manager IDs cannot be null")
    private List<Long> managerIds;
}
