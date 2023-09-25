package com.egt.gateway.config;

import com.egt.gateway.session.exception.RequestCurrentlyProcessingExeption;
import com.egt.gateway.session.exception.SessionAlreadyCompleteException;
import com.egt.gateway.session.exception.UnableToHandleSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RequestCurrentlyProcessingExeption.class)
    public ResponseEntity<Object> handleRequestCurrentlyProcessingException(
            RequestCurrentlyProcessingExeption ex) {

        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SessionAlreadyCompleteException.class)
    public ResponseEntity<Object> handleSessionAlreadyCompleteException(
            SessionAlreadyCompleteException ex) {

        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnableToHandleSessionException.class)
    public ResponseEntity<Object> handleUnableToHandleSessionException(
            UnableToHandleSessionException ex) {

        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
