package com.examportal.services;

import com.examportal.constant.GenericConstants;
import com.examportal.constant.MessageConstant;
import com.examportal.dtos.ApiResponseDTO;
import com.examportal.dtos.QuestionDTO;
import com.examportal.dtos.QuestionRespDTO;
import com.examportal.dtos.UpdateQuestionDTO;
import com.examportal.entities.Question;
import com.examportal.entities.Quiz;
import com.examportal.repositories.FeignClientService;
import com.examportal.repositories.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public QuestionRespDTO getQuestion(long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            QuestionRespDTO questionRespDTO = modelMapper.map(question, QuestionRespDTO.class);
            questionRespDTO.setQuiz(question.getQuizId() != null && this.getQuiz(question.getQuizId()) != null ? this.getQuiz(question.getQuizId()) : null);
            return questionRespDTO;
        }
        return null;
    }

    public List<QuestionRespDTO> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(question -> {
            QuestionRespDTO questionRespDTO = modelMapper.map(question, QuestionRespDTO.class);
            questionRespDTO.setQuiz(question.getQuizId() != null && this.getQuiz(question.getQuizId()) != null ? this.getQuiz(question.getQuizId()) : null);
            return questionRespDTO;
        }).collect(Collectors.toList());
    }

    public Question updateQuestion(UpdateQuestionDTO updateQuestionDTO) {
        Optional<Question> optionalQuestion = questionRepository.findById(updateQuestionDTO.getId());
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setContent(updateQuestionDTO.getContent() != null ? updateQuestionDTO.getContent() : question.getContent());
            question.setImage(updateQuestionDTO.getImage() != null ? updateQuestionDTO.getImage() : question.getImage());
            question.setOption1(updateQuestionDTO.getOption1() != null ? updateQuestionDTO.getOption1() : question.getOption1());
            question.setOption2(updateQuestionDTO.getOption2() != null ? updateQuestionDTO.getOption2() : question.getOption2());
            question.setOption3(updateQuestionDTO.getOption3() != null ? updateQuestionDTO.getOption3() : question.getOption3());
            question.setOption4(updateQuestionDTO.getOption4() != null ? updateQuestionDTO.getOption4() : question.getOption4());
            question.setAnswer(updateQuestionDTO.getAnswer() != null ? updateQuestionDTO.getAnswer() : question.getAnswer());
            return questionRepository.save(question);
        }
        return null;
    }

    public String deleteQuestion(long id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            questionRepository.delete(optionalQuestion.get());
            return MessageConstant.DELETE_QUESTION_SUCCESS;
        }
        return MessageConstant.QUESTION_NOT_FOUND;
    }
}
