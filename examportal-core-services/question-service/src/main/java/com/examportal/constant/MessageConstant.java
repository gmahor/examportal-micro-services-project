package com.examportal.constant;


public class MessageConstant {

    private MessageConstant() {
        throw new IllegalArgumentException("MessageConstant is a utility class");
    }

    public static final String REQUEST_ERROR = "Error in request:: {}";
    public static final String SUCCESS = "Success";
    public static final String INVALID_ROLE_ACCESS = "ACCESS_DENIED : User can't access this api.";


}
