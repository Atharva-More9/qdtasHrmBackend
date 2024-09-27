package com.qdtas.dto;

import com.qdtas.utils.NonZero;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AddRecruitmentDto {

    @NonZero
    @NotNull(message = "Job ID cannot be null")
    private Long job;            // Job ID (linked to the Job entity)

    @Min(value = 0, message = "Experience must be 0 or greater")
    private int experience;        // Number of years of experience

    @NonZero
    @NotNull(message = "User ID cannot be null")
    private Long user;           // User ID (linked to the User entity)

    @Pattern(regexp = "^(Open|Close)$", message = "Status can only be 'Open' or 'Close'")
    @NotBlank(message = "Status cannot be blank")
    private String status;         // Status of recruitment (Open/Close)
}

