package com.examportal.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpDTO {

    @NotBlank(message = "Username is required.")
    @Size(min = 2, max = 25, message = "Username must be between 5 and 25")
    private String username;

    @NotBlank(message = "FirstName is required. ")
    @Size(min = 2, max = 25, message = "FirstName must be between 5 and 25")
    private String firstName;

    @NotBlank(message = "LastName is required. ")
    @Size(min = 2, max = 25, message = "LastName must be between 5 and 25")
    private String lastName;

    @NotBlank(message = "Email is required. ")
    @Size(min = 5, max = 64, message = "Email must be between 5 and 64")
    private String email;

    @NotBlank(message = "Password is required. ")
    @Size(min = 5, max = 64, message = "Password must be between 5 and 64")
    private String password;

    @NotBlank(message = "Phone  is required.")
    @Size(min = 5, max = 25, message = "Phone must be between 5 and 25")
    private String phone;


    public SignUpDTO(String username, String firstName, String lastName, String email, String password, String phone) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
