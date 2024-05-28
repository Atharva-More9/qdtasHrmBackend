package com.qdtas.dto;

import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;

    @NotNull(message = "Name field should not be empty")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only alphabets,Name field should not be empty")
    private String name;

    @NotEmpty(message = "description should not be empty")
    private String description;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-mm-dd")
    //@DateTimeFormat(pattern = "yyyy-mm-dd")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Due date must be in the format yyyy-MM-dd")
    private String dueDate;

    @NotNull(message="assignee cannot be empty")
    private long assignee;

    private long empId;
}
