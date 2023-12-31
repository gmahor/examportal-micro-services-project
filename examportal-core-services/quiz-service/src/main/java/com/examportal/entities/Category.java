package com.examportal.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "category")
//@CrossOrigin("*")
public class Category extends BaseEntity {

    private String title;

    private String description;

    @OneToMany(targetEntity =Quiz.class ,mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Quiz> quizzer = new LinkedHashSet<>();

}
