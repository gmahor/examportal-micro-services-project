package com.examportal.constant;


public class MessageConstant {

    private MessageConstant() {
        throw new IllegalArgumentException("MessageConstant is a utility class");
    }

    public static final String REQUEST_ERROR = "Error in request:: {}";
    public static final String SUCCESS = "Success";
    public static final String INVALID_ROLE_ACCESS = "ACCESS_DENIED : User can't access this api.";
    public static final String QUESTION_ADDED_SUCCESS = "Question added successfully.";
    public static final String ERROR_ADDING_QUESTION = "Error while adding question.";
    public static final String ERROR_GETTING_QUESTION = "Error while getting question.";
    public static final String QUESTION_GET_SUCCESS = "Question fetched successfully.";
    public static final String QUESTION_NOT_FOUND = "Question not found.";
    public static final String ERROR_GETTING_QUESTIONS = "Error while getting all questions.";
    public static final String QUESTIONS_GET_SUCCESS = "Questions fetched successfully.";
    public static final String QUESTION_UPDATED_SUCCESS = "Question updated successfully.";
    public static final String ERROR_UPDATED_QUESTION = "Error while updating question.";
    public static final String DELETE_QUESTION_SUCCESS = "Question deleted successfully.";
    public static final String ERROR_DELETE_QUESTION = "Error while deleting question.";
}
