package com.examportal.repositories;

import com.examportal.constant.GenericConstants;
import com.examportal.dtos.ApiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = "quiz-service", url = "${backendServerDetail}")
public interface FeignClientService {

    @GetMapping("/api/v1/quiz/{id}")
    ApiResponseDTO getQuiz(@PathVariable(name = "id") Long id, @RequestHeader(GenericConstants.AUTHORIZATION) String bearerToken);

    @GetMapping("/api/v1/quiz/quizExists")
    ApiResponseDTO quizExists(@RequestParam(name = "id") Long id, @RequestHeader(GenericConstants.AUTHORIZATION) String bearerToken);


}
