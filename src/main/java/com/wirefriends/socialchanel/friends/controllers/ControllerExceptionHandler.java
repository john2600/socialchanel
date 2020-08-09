package com.wirefriends.socialchanel.friends.controllers;

import com.wirefriends.socialchanel.friends.exceptions.MessageErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.ValidationException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    MessageErrors exceptionHandler(ValidationException e){
        return new MessageErrors(String.valueOf(HttpStatus.BAD_REQUEST), e.getMessage());
    }

}
