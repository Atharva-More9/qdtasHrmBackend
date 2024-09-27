package com.qdtas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecruitmentDto {

    private long job; // Job Id

    private int experience; // Number of years of experience

    private long user; // User Id

    private String status; // Status ('Open', 'Close')

}
