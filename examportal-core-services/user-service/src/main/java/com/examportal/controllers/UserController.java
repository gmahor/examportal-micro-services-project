package com.examportal.controllers;

import com.examportal.constant.MessageConstant;
import com.examportal.dtos.SignInDTO;
import com.examportal.dtos.SignUpDTO;
import com.examportal.entities.User;
import com.examportal.services.UserService;
import com.examportal.utils.CommonUtil;
import com.examportal.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    private final UserService userService;


    private final ResponseHandler responseHandler;

    private final CommonUtil commonUtil;

    public UserController(ResponseHandler responseHandler,
                          UserService userService,
                          CommonUtil commonUtil) {
        this.responseHandler = responseHandler;
        this.userService = userService;
        this.commonUtil = commonUtil;
    }


    @PostMapping(value = "/sign-up")
    public ResponseEntity<Object> signUp(@Validated @RequestBody SignUpDTO signUpDTO, BindingResult bindingResult) {
        try {
            log.info("Sign up request received by : {}", signUpDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            String msg = userService.signUp(signUpDTO);
            log.info(msg);
            if (msg.equals(MessageConstant.SIGN_UP_SUCCESS))
                return responseHandler.generateResponse("", msg, true, HttpStatus.OK);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_SIGN_UP, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_SIGN_UP, false, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/getUser")
    public ResponseEntity<Object> getUser(@RequestParam(name = "id") Long id) {
        try {
            log.info("Get user request received by : {}", id);
            User user = userService.getUser(id);
            if (user != null) {
                ResponseEntity<Object> objResponseEntity = commonUtil.notASameUser(user);
                if (objResponseEntity != null) {
                    return objResponseEntity;
                }
                return responseHandler.response(user, MessageConstant.GET_USER_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.USER_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_GETTING_USER, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_GETTING_USER, false, HttpStatus.BAD_GATEWAY);
    }

    @PostMapping(value = "/sign-in")
    public ResponseEntity<Object> signIn(@Validated @RequestBody SignInDTO signInDTO, BindingResult bindingResult) {
        try {
            log.info("SignIn request received by : {}", signInDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            Object signObj = userService.signIn(signInDTO);
            if (signObj != null && signObj.equals(MessageConstant.USER_NOT_FOUND)) {
                return responseHandler.response("", MessageConstant.USER_NOT_FOUND, false, HttpStatus.NOT_FOUND);
            } else if (signObj != null && signObj.equals(MessageConstant.IN_CORRECT_PASSWORD)) {
                return responseHandler.response("", MessageConstant.IN_CORRECT_PASSWORD, false, HttpStatus.BAD_GATEWAY);
            } else if (signObj != null && signObj.equals(MessageConstant.ROLE_NOT_USER)) {
                return responseHandler.response("", MessageConstant.ROLE_NOT_USER, false, HttpStatus.BAD_GATEWAY);
            }
            return responseHandler.response(signObj, MessageConstant.SIGN_IN_SUCCESS, true, HttpStatus.OK);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_SIGN_IN, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_SIGN_IN, false, HttpStatus.BAD_GATEWAY);
    }
}
