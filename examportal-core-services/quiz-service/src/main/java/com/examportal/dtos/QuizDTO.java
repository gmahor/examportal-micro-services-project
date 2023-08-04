package com.examportal.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class QuizDTO {

    @NotBlank(message = "Title is required.")
    @Size(min = 4, message = "Title must be minimum 4 char.")
    private String title;

    @NotBlank(message = "Description is required.")
    @Size(min = 10, message = "Description must be minimum 10 char.")
    private String description;

    @NotBlank(message = "MaxMarks is required.")
    private String maxMarks;

    @NotBlank(message = "Number of question is required.")
    private String numberOfQuestions;

    @Min(value = 1, message = "category id is required.")
    private Long cat_id;

}
