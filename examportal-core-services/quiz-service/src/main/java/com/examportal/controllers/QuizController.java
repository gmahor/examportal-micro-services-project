package com.examportal.controllers;

import com.examportal.constant.MessageConstant;
import com.examportal.dtos.QuizDTO;
import com.examportal.dtos.UpdateQuizDTO;
import com.examportal.entities.Quiz;
import com.examportal.services.QuizService;
import com.examportal.utils.CommonUtilService;
import com.examportal.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {


    private final QuizService quizService;

    private final ResponseHandler responseHandler;


    private final CommonUtilService commonUtilService;

    public QuizController(QuizService quizService,
                          ResponseHandler responseHandler,
                          CommonUtilService commonUtilService) {
        this.quizService = quizService;
        this.responseHandler = responseHandler;
        this.commonUtilService = commonUtilService;
    }


    @PostMapping(value = "/addQuiz")
    public ResponseEntity<Object> addQuiz(@Validated @RequestBody QuizDTO quizDTO, BindingResult bindingResult) {
        try {
            log.info("Add quiz request required by : {}", quizDTO);
            if (bindingResult.hasErrors()) {
                return commonUtilService.requestValidation(bindingResult);
            }
            ResponseEntity<Object> invalidRoleAccess = commonUtilService.invalidRoleAccess();
            if (invalidRoleAccess != null) {
                return invalidRoleAccess;
            }
            Object quiz = quizService.addQuiz(quizDTO);
            if (quiz != null && quiz.equals(MessageConstant.CATEGORY_NOT_FOUND)) {
                return responseHandler.response("", MessageConstant.CATEGORY_NOT_FOUND, false, HttpStatus.NOT_FOUND);
            }
            return responseHandler.response(quiz, MessageConstant.QUIZ_ADD_SUCCESS, true, HttpStatus.OK);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_ADD_QUIZ, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_ADD_QUIZ, false, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping(value = "/getAllQuiz")
    public ResponseEntity<Object> getAllQuiz() {
        try {
            log.info("Get all category request received.");
            List<Quiz> allQuiz = quizService.getAllQuiz();
            if (!allQuiz.isEmpty()) {
                return responseHandler.response(allQuiz, MessageConstant.GET_ALL_QUIZ_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.QUIZ_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_GET_ALL_QUIZ, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_GET_ALL_QUIZ, false, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getQuiz(@PathVariable(name = "id") Long id) {
        try {
            log.info("Get category request received by id : {}", id);
            Quiz quiz = quizService.getQuiz(id);
            if (quiz != null) {
                return responseHandler.response(quiz, MessageConstant.GET_QUIZ_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.QUIZ_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_GET_QUIZ, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_GET_QUIZ, false, HttpStatus.BAD_GATEWAY);
    }

    @DeleteMapping(value = "/deleteQuiz")
    public ResponseEntity<Object> deleteQuiz(@RequestParam("id") Long id) {
        try {
            log.info("Delete quiz request received by id : {}", id);
            ResponseEntity<Object> invalidRoleAccess = commonUtilService.invalidRoleAccess();
            if (invalidRoleAccess != null) {
                return invalidRoleAccess;
            }
            String dltMsg = quizService.deleteQuiz(id);
            if (dltMsg.equals(MessageConstant.DELETE_QUIZ_SUCCESS)) {
                return responseHandler.response("", dltMsg, true, HttpStatus.OK);
            }
            return responseHandler.response("", dltMsg, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_DELETE_QUIZ, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_DELETE_QUIZ, false, HttpStatus.BAD_GATEWAY);
    }

    @PatchMapping(value = "/updateQuiz")
    public ResponseEntity<Object> updateQuiz(@Validated @RequestBody UpdateQuizDTO updateQuizDTO, BindingResult bindingResult) {
        try {
            log.info("Update quiz request received.");
            if (bindingResult.hasErrors()) {
                return commonUtilService.requestValidation(bindingResult);
            }
            ResponseEntity<Object> invalidRoleAccess = commonUtilService.invalidRoleAccess();
            if (invalidRoleAccess != null) {
                return invalidRoleAccess;
            }
            Quiz quiz = quizService.updateQuiz(updateQuizDTO);
            if (quiz != null) {
                return responseHandler.response(quiz, MessageConstant.QUIZ_UPDATE_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.QUIZ_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_UPDATE_CATEGORY, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_UPDATE_QUIZ, false, HttpStatus.BAD_GATEWAY);
    }


}
