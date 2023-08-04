package com.examportal.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {


    @GetMapping(value = "/createQuiz")
    public ResponseEntity<Object> createQuiz() {
        return ResponseEntity.ok("success");
    }


}
