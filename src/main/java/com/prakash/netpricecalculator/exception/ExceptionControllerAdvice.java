package com.prakash.netpricecalculator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    /**
     * Constraint violation and invalid request exception handler
     *
     * @param ex       ConstraintViolationException
     * @param response HttpServletResponse
     * @throws IOException
     */
    @ExceptionHandler({InvalidRequestException.class, ConstraintViolationException.class, HttpClientErrorException.class})
    public void handleExceptions(Exception ex, HttpServletResponse response) throws IOException {
        log.error(ex.getMessage(), ex);
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Bad Request");
    }
}
