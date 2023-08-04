package com.examportal.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CategoryDTO {

    @NotBlank(message = "Title is required.")
    @Size(min = 5, message = "Title must be minimum 5 character.")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must be minimum 10 character.")
    private String description;
}
