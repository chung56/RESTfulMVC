package com.example.MVCSpringBoot.Exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.example.MVCSpringBoot.Constants.ApiConstants.DATE_FORMAT;

@Data
public class ApiError {

    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime timeStamp;
    private String message;
    private String debugMessage;
    private String path;

    public ApiError() {
    }

    ApiError( HttpStatus status )
    {
        this();
        this.httpStatus = status;
    }

    ApiError( HttpStatus status, Throwable ex )
    {
        this(status);
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError( HttpStatus status, String message, Throwable ex )
    {
        this(status,ex);
        this.message = message;
    }

    ApiError( HttpStatus status, String message,String path, Throwable ex )
    {
        this(status,message,ex);
        this.path = path;
    }
}
