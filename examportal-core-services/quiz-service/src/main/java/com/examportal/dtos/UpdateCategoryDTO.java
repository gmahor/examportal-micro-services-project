package com.examportal.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class UpdateCategoryDTO {

    @Min(value = 1, message = "Id is required.")
    private Long id;


    @Size(min = 2, message = "Title must be minimum 2 character.")
    private String title;


    @Size(min = 8, message = "Description must be minimum 10 character.")
    private String description;
}
