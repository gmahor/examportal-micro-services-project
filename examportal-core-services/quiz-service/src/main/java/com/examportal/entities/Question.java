package com.examportal.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Getter
@Setter
@Entity
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;


}
