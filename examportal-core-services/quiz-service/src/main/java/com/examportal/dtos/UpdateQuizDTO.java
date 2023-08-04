package com.examportal.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class UpdateQuizDTO {

    @Min(value = 1, message = "Id  is required.")
    public Long id;

    @Size(min = 4, message = "Title must be minimum 4 char.")
    private String title;

    @Size(min = 10, message = "Description must be minimum 10 char.")
    private String description;

    private String maxMarks;

    private String numberOfQuestions;

}
