package com.examportal.controllers;

import com.examportal.constant.MessageConstant;
import com.examportal.dtos.QuestionDTO;
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
}
