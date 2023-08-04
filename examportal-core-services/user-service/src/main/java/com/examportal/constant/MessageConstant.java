package com.examportal.constant;


public class MessageConstant {

    private MessageConstant() {
        throw new IllegalArgumentException("MessageConstant is a utility class");
    }

    public static final String REQUEST_ERROR = "Error in request:: {}";
    public static final String SIGN_UP_SUCCESS = "SignUp successfully.";
    public static final String ERROR_SIGN_UP = "Error while signup user.";
    public static final String GET_USER_SUCCESS = "User get successfully.";
    public static final String ERROR_GETTING_USER = "Error while getting user.";
    public static final String ACCESS_DENIED = "ACCESS_DENIED : You don't have access to use this api. your are using some other user access token.";
    public static final String ROLE_NOT_USER = "ACCESS_DENIED : You don't have access to use this api. your role is not USER.";
    public static final String ROLE_NOT_ADMIN = "ACCESS_DENIED : You don't have access to use this api. your role is not ADMIN.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String SIGN_IN_SUCCESS = "Login successfully.";
    public static final String ERROR_SIGN_IN = "Error while login.";
    public static final String IN_CORRECT_PASSWORD = "Password is incorrect.";


}
