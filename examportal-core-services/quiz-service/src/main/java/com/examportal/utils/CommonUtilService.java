package com.examportal.utils;


import com.examportal.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class CommonUtilService {

    private final HttpServletRequest httpServletRequest;

    private final ResponseHandler responseHandler;

    public CommonUtilService(HttpServletRequest httpServletRequest,
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

}
