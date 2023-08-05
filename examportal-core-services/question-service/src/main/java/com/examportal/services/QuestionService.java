package com.examportal.services;

import com.examportal.constant.GenericConstants;
import com.examportal.dtos.ApiResponseDTO;
import com.examportal.dtos.QuestionDTO;
import com.examportal.entities.Question;
import com.examportal.entities.Quiz;
import com.examportal.repositories.FeignClientService;
import com.examportal.repositories.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final FeignClientService feignClientService;

    private final HttpServletRequest servletRequest;

    public final ModelMapper modelMapper;

    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           FeignClientService feignClientService,
                           HttpServletRequest servletRequest,
                           ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.feignClientService = feignClientService;
        this.servletRequest = servletRequest;
        this.modelMapper = modelMapper;
    }

    public Question addQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setContent(questionDTO.getContent());
        question.setImage(questionDTO.getImage());
        question.setOption1(questionDTO.getOption1());
        question.setOption2(questionDTO.getOption2());
        question.setOption3(questionDTO.getOption3());
        question.setOption4(questionDTO.getOption4());
        question.setAnswer(questionDTO.getAnswer());
        if (quizExists(questionDTO.getQuiz_id())) {
            question.setQuizId(questionDTO.getQuiz_id());
        }
        return questionRepository.save(question);
    }


    public Quiz getQuiz(long id) {
        try {
            String token = servletRequest.getHeader(GenericConstants.AUTHORIZATION);
            ApiResponseDTO apiResponseDTO = feignClientService.getQuiz(id, token);
            if (apiResponseDTO != null) {
                return modelMapper.map(apiResponseDTO.getData(), Quiz.class);
            }
        } catch (Exception e) {
            log.error("ERROR while getting quiz - ", e);
        }
        return null;
    }

    public boolean quizExists(long id) {
        try {
            String token = servletRequest.getHeader(GenericConstants.AUTHORIZATION);
            ApiResponseDTO apiResponseDTO = feignClientService.quizExists(id, token);
            if (apiResponseDTO != null && apiResponseDTO.getData() != null) {
                return true;
            }
        } catch (Exception e) {
            log.error("ERROR while searching quiz - ", e);
        }
        return false;
    }

}
