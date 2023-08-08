package com.examportal.controllers;

import com.examportal.constant.MessageConstant;
import com.examportal.dtos.QuestionDTO;
import com.examportal.dtos.QuestionRespDTO;
import com.examportal.dtos.UpdateQuestionDTO;
import com.examportal.entities.Question;
import com.examportal.services.QuestionService;
import com.examportal.utils.CommonUtil;
import com.examportal.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/question")
@CrossOrigin("*")
public class QuestionController {

    public final QuestionService questionService;

    public final ResponseHandler responseHandler;

    public final CommonUtil commonUtil;

    @Autowired
    public QuestionController(QuestionService questionService,
                              ResponseHandler responseHandler,
                              CommonUtil commonUtil) {
        this.questionService = questionService;
        this.responseHandler = responseHandler;
        this.commonUtil = commonUtil;

    }

    @PostMapping(value = "/addQuestion")
    public ResponseEntity<Object> addQuestion(@Validated @RequestBody QuestionDTO questionDTO, BindingResult bindingResult) {
        try {
            log.info("Add question request received by : {}", questionDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            ResponseEntity<Object> responseEntity = commonUtil.invalidRoleAccess();
            if (responseEntity != null) {
                return responseEntity;
            }
            Question question = questionService.addQuestion(questionDTO);
            if (question != null)
                return responseHandler.response(question, MessageConstant.QUESTION_ADDED_SUCCESS, true, HttpStatus.OK);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_ADDING_QUESTION, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_ADDING_QUESTION, false, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping(value = "/getQuestion")
    public ResponseEntity<Object> getQuestion(@RequestParam(name = "id") Long id) {
        try {
            log.info("Get question request received by id : {}", id);
            QuestionRespDTO question = questionService.getQuestion(id);
            if (question != null) {
                return responseHandler.response(question, MessageConstant.QUESTION_GET_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.QUESTION_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_GETTING_QUESTION, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_GETTING_QUESTION, false, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getQuestions")
    public ResponseEntity<Object> getQuestions() {
        try {
            log.info("Get questions request received ");
            List<QuestionRespDTO> questions = questionService.getQuestions();
            if (!questions.isEmpty()) {
                return responseHandler.response(questions, MessageConstant.QUESTIONS_GET_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.QUESTION_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_GETTING_QUESTIONS, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_GETTING_QUESTIONS, false, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping(value = "/updateQuestion")
    public ResponseEntity<Object> updateQuestion(@RequestBody UpdateQuestionDTO updateQuestionDTO) {
        try {
            log.info("Update questions request received by : {}", updateQuestionDTO);
            ResponseEntity<Object> responseEntity = commonUtil.invalidRoleAccess();
            if (responseEntity != null) {
                return responseEntity;
            }
            Question updateQuestion = questionService.updateQuestion(updateQuestionDTO);
            if (updateQuestion != null) {
                return responseHandler.response(updateQuestion, MessageConstant.QUESTION_UPDATED_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.QUESTION_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_UPDATED_QUESTION, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_UPDATED_QUESTION, false, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/deleteQuestion")
    public ResponseEntity<Object> deleteQuestion(@RequestParam(name = "id") Long id) {
        try {
            log.info("Delete question request received by : {}", id);
            String msg = questionService.deleteQuestion(id);
            if (msg.equals(MessageConstant.DELETE_QUESTION_SUCCESS)) {
                return responseHandler.response("", MessageConstant.DELETE_QUESTION_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.QUESTION_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_DELETE_QUESTION, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_DELETE_QUESTION, false, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping(value = "/questionsByQuiz")
    public ResponseEntity<Object> questionsByQuiz(@RequestParam(name = "quizId") Long quizId) {
        try {
            log.info("Get questions by quizId request received by quizId : {}", quizId);
            List<QuestionRespDTO> questionRespDTOS = questionService.questionsByQuiz(quizId);
            if (!questionRespDTOS.isEmpty()) {
                return responseHandler.response(questionRespDTOS, MessageConstant.QUESTIONS_GET_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.QUESTION_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_GETTING_QUESTIONS, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_GETTING_QUESTIONS, false, HttpStatus.BAD_REQUEST);
    }

}
