package com.examportal.dtos;

import lombok.Data;

@Data
public class ApiResponseDTO {

    private String timeStamp;

    private Object data;

    private String message;

    private boolean isSuccess;

    private int statusCode;

}
