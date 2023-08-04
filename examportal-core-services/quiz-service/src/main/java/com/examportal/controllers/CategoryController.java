package com.examportal.controllers;


import com.examportal.config.EnvConfiguration;
import com.examportal.constant.MessageConstant;
import com.examportal.dtos.CategoryDTO;
import com.examportal.entities.Category;
import com.examportal.services.CategoryService;
import com.examportal.utils.CommonUtilService;
import com.examportal.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/cat")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    private final ResponseHandler responseHandler;

    private final HttpServletRequest servletContext;


    private final EnvConfiguration envConfiguration;


    private final CommonUtilService commonUtilService;

    @Autowired
    public CategoryController(CategoryService categoryService,
                              ResponseHandler responseHandler,
                              HttpServletRequest servletContext,
                              EnvConfiguration envConfiguration,
                              CommonUtilService commonUtilService) {
        this.categoryService = categoryService;
        this.responseHandler = responseHandler;
        this.servletContext = servletContext;
        this.envConfiguration = envConfiguration;
        this.commonUtilService = commonUtilService;
    }

    @PostMapping(value = "/addCategory")
    public ResponseEntity<Object> addCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            log.info("Added category request received by : {}", categoryDTO);
            ResponseEntity<Object> invalidRoleAccess = commonUtilService.invalidRoleAccess();
            if (invalidRoleAccess != null) {
                return invalidRoleAccess;
            }
            Category category = categoryService.addCategory(categoryDTO);
            if (category != null)
                return responseHandler.response(category, MessageConstant.ADD_CATEGORY_SUCCESS, true, HttpStatus.OK);
            return responseHandler.response("", MessageConstant.ADD_CATEGORY_SUCCESS, true, HttpStatus.OK);
        } catch (Exception e) {
            log.info(MessageConstant.ERROR_ADD_CATEGORY, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_ADD_CATEGORY, false, HttpStatus.BAD_GATEWAY);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getCategory(@PathVariable(name = "id") Long id) {
        try {
            log.info("Get category request received by id : {}", id);
            Category category = categoryService.getCategory(id);
            if (category != null) {
                return responseHandler.response(category, MessageConstant.GET_CATEGORY_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.CATEGORY_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_GET_CATEGORY, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_GET_CATEGORY, false, HttpStatus.BAD_GATEWAY);
    }


}
