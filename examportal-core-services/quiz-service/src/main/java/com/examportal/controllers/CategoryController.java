package com.examportal.controllers;


import com.examportal.config.EnvConfiguration;
import com.examportal.constant.MessageConstant;
import com.examportal.dtos.CategoryDTO;
import com.examportal.dtos.UpdateCategoryDTO;
import com.examportal.entities.Category;
import com.examportal.services.CategoryService;
import com.examportal.utils.CommonUtilService;
import com.examportal.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResponseEntity<Object> addCategory(@Validated @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        try {
            log.info("Added category request received by : {}", categoryDTO);
            if (bindingResult.hasErrors()) {
                return commonUtilService.requestValidation(bindingResult);
            }
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

    @GetMapping(value = "/getAllCategory")
    public ResponseEntity<Object> getAllCategory() {
        try {
            log.info("Get all category request received.");
            List<Category> categories = categoryService.getAllCategory();
            if (!categories.isEmpty()) {
                return responseHandler.response(categories, MessageConstant.GET_ALL_CATEGORY_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.CATEGORY_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_GET_ALL_CATEGORY, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_GET_ALL_CATEGORY, false, HttpStatus.BAD_GATEWAY);
    }

    @PatchMapping(value = "/updateCategory")
    public ResponseEntity<Object> updateCategory(@Validated @RequestBody UpdateCategoryDTO updateCategoryDTO, BindingResult bindingResult) {
        try {
            log.info("Update category request received.");
            if (bindingResult.hasErrors()) {
                return commonUtilService.requestValidation(bindingResult);
            }
            ResponseEntity<Object> invalidRoleAccess = commonUtilService.invalidRoleAccess();
            if (invalidRoleAccess != null) {
                return invalidRoleAccess;
            }
            Category category = categoryService.updateCategory(updateCategoryDTO);
            if (category != null) {
                return responseHandler.response(category, MessageConstant.CATEGORY_UPDATE_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", MessageConstant.CATEGORY_NOT_FOUND, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_UPDATE_CATEGORY, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_UPDATE_CATEGORY, false, HttpStatus.BAD_GATEWAY);
    }

    @DeleteMapping(value = "/deleteCategory")
    public ResponseEntity<Object> deleteCategory(@RequestParam("id") Long id) {
        try {
            log.info("Delete category request received by id : {}", id);
            ResponseEntity<Object> invalidRoleAccess = commonUtilService.invalidRoleAccess();
            if (invalidRoleAccess != null) {
                return invalidRoleAccess;
            }
            String dltMsg = categoryService.deleteCategory(id);
            if (dltMsg.equals(MessageConstant.DELETE_CATEGORY_SUCCESS)) {
                return responseHandler.response("", MessageConstant.CATEGORY_UPDATE_SUCCESS, true, HttpStatus.OK);
            }
            return responseHandler.response("", dltMsg, false, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(MessageConstant.ERROR_DELETE_CATEGORY, e);
        }
        return responseHandler.response("", MessageConstant.ERROR_DELETE_CATEGORY, false, HttpStatus.BAD_GATEWAY);
    }
}
