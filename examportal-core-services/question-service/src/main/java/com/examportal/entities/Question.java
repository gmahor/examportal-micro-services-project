package com.examportal.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "question")
public class Question extends BaseEntity {

    @Column(length = 5000)
    private String content;

    private String image = "question.jpg";

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String answer;

    // bz of Transient the givenAnswer field is not add in DB.
    @Transient
    private String givenAnswer;

    private Long quizId;

}
