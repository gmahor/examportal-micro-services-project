package com.examportal.controllers;

import com.examportal.constant.MessageConstant;
import com.examportal.dtos.SignInDTO;
import com.examportal.services.AdminService;
import com.examportal.services.UserService;
import com.examportal.utils.CommonUtil;
import com.examportal.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ResponseHandler responseHandler;

    private final CommonUtil commonUtil;

    private final AdminService adminService;

    @Autowired
    public AdminController(ResponseHandler responseHandler,
                           CommonUtil commonUtil,
                           AdminService adminService) {
        this.responseHandler = responseHandler;
        this.commonUtil = commonUtil;
        this.adminService = adminService;
    }

    @PostMapping(value = "/admin-sign-in")
    public ResponseEntity<Object> adminSignIn(@Validated @RequestBody SignInDTO signInDTO, BindingResult bindingResult) {
        try {
            log.info("Admin sign in request received by : {}", signInDTO);
            if (bindingResult.hasErrors()) {
                return commonUtil.requestValidation(bindingResult);
            }
            Object adminSignObj = adminService.createAdmin(signInDTO);
            if (adminSignObj != null && adminSignObj.equals(MessageConstant.IN_CORRECT_PASSWORD)) {
                return responseHandler.response("", MessageConstant.IN_CORRECT_PASSWORD, false, HttpStatus.BAD_GATEWAY);
            } else if (adminSignObj != null && adminSignObj.equals(MessageConstant.ROLE_NOT_USER)) {
                return responseHandler.response("", MessageConstant.ROLE_NOT_ADMIN, false, HttpStatus.BAD_GATEWAY);
            } else if (adminSignObj != null && adminSignObj.equals(MessageConstant.USER_NOT_FOUND)) {
                return responseHandler.response("", MessageConstant.USER_NOT_FOUND, false, HttpStatus.NOT_FOUND);
            } else
                return responseHandler.response(adminSignObj, MessageConstant.SIGN_IN_SUCCESS, true, HttpStatus.OK);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_SIGN_IN, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_SIGN_IN, false, HttpStatus.BAD_GATEWAY);
    }


}
