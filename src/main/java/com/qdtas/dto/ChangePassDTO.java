package com.qdtas.dto;

import com.qdtas.utils.NoSpaces;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassDTO {

    private String email;

    @NoSpaces(message = "temp Password Should not contain spaces")
    @NotBlank(message = "temp Password cannot be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,14}$", message = "temp Password should contain at least one digit, one special character and one lowercase and uppercase alphabate")
    private String oldP;

    @NoSpaces(message = "Old Password Should not contain spaces")
    @NotBlank(message = "Old Password cannot be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,14}$", message = "NEW Password should contain at least one digit, one special character and one lowercase and uppercase alphabate")
    private String newP;

}
