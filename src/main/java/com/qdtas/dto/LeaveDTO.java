package com.qdtas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDTO {

    private long leaveId;
    @Hidden
    private long employeeId;

    @NotNull(message = "Please provide a valid birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull(message = "Please provide a valid birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    @NotNull(message = "please provide leave type.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    private String type;

    @NotBlank(message = "Reason cannot be blank")
    private String reason;
    @Hidden
    private String status;
}
