package com.examportal.dtos;

import com.examportal.entities.Quiz;
import lombok.Data;

@Data
public class QuestionRespDTO {

    private Long id;

    private String content;

    private String image;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String answer;

    private Quiz quiz;

}
