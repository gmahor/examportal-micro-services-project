package com.examportal.constant;


public class MessageConstant {

    private MessageConstant() {
        throw new IllegalArgumentException("MessageConstant is a utility class");
    }

    public static final String REQUEST_ERROR = "Error in request:: {}";
    public static final String SUCCESS = "Success";
    public static final String ADD_CATEGORY_SUCCESS = "Category added successfully";
    public static final String ERROR_ADD_CATEGORY = "Error while adding category";
    public static final String INVALID_ROLE_ACCESS = "ACCESS_DENIED : User can't access this api.";
    public static final String GET_CATEGORY_SUCCESS = "Category fetched successfully.";
    public static final String ERROR_GET_CATEGORY = "Error while fetching category.";
    public static final String CATEGORY_NOT_FOUND = "Category not found.";
    public static final String GET_ALL_CATEGORY_SUCCESS = "Categories fetched successfully.";
    public static final String ERROR_GET_ALL_CATEGORY = "Error while fetching all category.";
    public static final String CATEGORY_UPDATE_SUCCESS = "Category updated successfully.";
    public static final String ERROR_UPDATE_CATEGORY = "Error while updating category.";
    public static final String DELETE_CATEGORY_SUCCESS = "Category deleted successfully.";
    public static final String ERROR_DELETE_CATEGORY = "Error while deleting category.";
    public static final String QUIZ_ADD_SUCCESS = "Quiz added successfully.";
    public static final String ERROR_ADD_QUIZ = "Error while adding quiz.";
    public static final String QUIZ_NOT_FOUND = "Quiz not found.";
    public static final String DELETE_QUIZ_SUCCESS = "Quiz deleted successfully.";
    public static final String ERROR_DELETE_QUIZ = "Error while deleting quiz.";
    public static final String GET_ALL_QUIZ_SUCCESS = "All quiz fetched successfully.";
    public static final String ERROR_GET_ALL_QUIZ = "Error while fetching all quiz.";
    public static final String GET_QUIZ_SUCCESS = "Quiz fetched successfully.";
    public static final String ERROR_GET_QUIZ = "Error while fetching quiz.";
    public static final String QUIZ_UPDATE_SUCCESS = "Quiz updated successfully.";
    public static final String ERROR_UPDATE_QUIZ = "Error while updating quiz.";
}
