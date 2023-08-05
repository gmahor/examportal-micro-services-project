package com.examportal.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class QuestionDTO {

    @NotBlank(message = "Content is required.")
    private String content;

    private String image = "question.jpg";

    @NotBlank(message = "Option1 is required.")
    private String option1;

    @NotBlank(message = "Option2 is required.")
    private String option2;

    @NotBlank(message = "Option3 is required.")
    private String option3;

    @NotBlank(message = "Option4 is required.")
    private String option4;

    @NotBlank(message = "Answer is required.")
    private String answer;

    @Min(value = 1, message = "Quiz id is required")
    private Long quiz_id;


}
