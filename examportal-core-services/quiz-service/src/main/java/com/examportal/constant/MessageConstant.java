package com.examportal.constant;


public class MessageConstant {

    private MessageConstant() {
        throw new IllegalArgumentException("MessageConstant is a utility class");
    }


    public static final String SUCCESS = "Success";
    public static final String ADD_CATEGORY_SUCCESS = "Category added successfully";
    public static final String ERROR_ADD_CATEGORY = "Error while adding category";
    public static final String INVALID_ROLE_ACCESS = "ACCESS_DENIED : User can't access this api.";
    public static final String GET_CATEGORY_SUCCESS = "Category fetched successfully.";
    public static final String ERROR_GET_CATEGORY = "Error while fetching category.";
    public static final String CATEGORY_NOT_FOUND = "Category not found.";


}
