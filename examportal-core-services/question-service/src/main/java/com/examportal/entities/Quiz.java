package com.examportal.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quiz {

    private Long id;


    private String title;

    private String description;

    private String maxMarks;

    private String numberOfQuestions;

    private boolean active;

    private Category category;

}
