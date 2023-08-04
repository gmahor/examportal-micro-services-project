package com.examportal.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignInDTO {

    @NotBlank(message = "Username is required.")
    @Size(min = 5, max = 64, message = "Username size must be between 5 to 64 chars.")
    private String username;

    @NotNull(message = "Password is required.")
    @Size(min = 8, max = 16, message = "Password size must be between 5 to 16 chars.")
    private String password;

}
