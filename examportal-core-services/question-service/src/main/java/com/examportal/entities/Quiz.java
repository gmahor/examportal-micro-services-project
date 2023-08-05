package com.examportal.entities;

import lombok.Data;

@Data
public class Quiz extends BaseEntity {

    private String title;

    private String description;

    private String maxMarks;

    private String numberOfQuestions;

    private boolean active = false;

    private Category category;

}
