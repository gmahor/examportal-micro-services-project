package com.examportal.utils;

import com.examportal.constant.MessageConstant;
import com.examportal.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class CommonUtil {

    private final HttpServletRequest httpServletRequest;

    private final ResponseHandler responseHandler;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public CommonUtil(HttpServletRequest httpServletRequest,
                      ResponseHandler responseHandler,
                      PasswordEncoder passwordEncoder) {
        this.httpServletRequest = httpServletRequest;
        this.responseHandler = responseHandler;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Object> notASameUser(User user) {
        long id = Long.parseLong(httpServletRequest.getHeader("id"));
        if (id != 0L && id != user.getId()) {
            return responseHandler.response("", MessageConstant.ACCESS_DENIED, false, HttpStatus.BAD_GATEWAY);
        }
        return null;
    }

    public ResponseEntity<Object> requestValidation(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            errorMessage.append(error.getDefaultMessage()).append(". ");
        }
        log.info(MessageConstant.REQUEST_ERROR, errorMessage);
        return responseHandler.response("", errorMessage.toString(), false,
                HttpStatus.BAD_REQUEST);
    }

    public String accessBasedOnRole() {
        String roles = httpServletRequest.getHeader("roles");
        log.info("roles : {}", roles);
        return roles;

    }

    public String isPasswordMatched(String rawPassword, String encodedPassword) {
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        if(!matches){
            return MessageConstant.IN_CORRECT_PASSWORD;
        }
        return null;
    }


}
