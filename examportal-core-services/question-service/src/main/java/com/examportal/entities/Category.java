package com.examportal.entities;

import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;


@Data
public class Category extends BaseEntity {

    private String title;

    private String description;

    private Set<Quiz> quizzer = new LinkedHashSet<>();

}
