package com.qdtas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qdtas.utils.NonZero;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Timesheet {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Hidden
    private long timeSheetId;

    @NotNull(message = "Start Time Should Not Be Null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name = "start_time")
    private LocalTime startTime;

    @NotNull(message = "End Time Should Not Be Null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name = "end_time")
    private LocalTime endTime;

    @NotNull(message = "Date Should Not Be Null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @NotBlank(message = "Note Should Not Be Null")
    private String note;

    @Hidden
    private long empId;

    @NotNull(message = "Project ID Should Not Be Null")
    @NonZero( message = "Project ID should contain only numbers")
    private long projectId;
}
