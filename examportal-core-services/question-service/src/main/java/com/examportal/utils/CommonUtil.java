package com.examportal.utils;

import com.examportal.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class CommonUtil {

    private final HttpServletRequest httpServletRequest;

    private final ResponseHandler responseHandler;

    public CommonUtil(HttpServletRequest httpServletRequest,
                      ResponseHandler responseHandler) {
        this.httpServletRequest = httpServletRequest;
        this.responseHandler = responseHandler;
    }

    public ResponseEntity<Object> invalidRoleAccess() {
        String role = httpServletRequest.getHeader("roles");
        if (!role.equals("ADMIN")) {
            return responseHandler.response("", MessageConstant.INVALID_ROLE_ACCESS, false, HttpStatus.BAD_GATEWAY);
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
}
