package com.epam.esm.exception.handler;

import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.handler.dto.ResourceExceptionDto;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceException.class)
    public ResourceExceptionDto handleNotFoundId(ResourceException e) {
        return new ResourceExceptionDto(e.getHttpStatus(), e.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NumberFormatException.class, MethodArgumentTypeMismatchException.class,
        ConstraintViolationException.class})
    public ResourceExceptionDto handleNumberFormatException(Exception e) {
        return new ResourceExceptionDto(HttpStatus.BAD_REQUEST, "Id isn't correct");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResourceExceptionDto handleHttpMessageConversionException(HttpMessageConversionException e) {
        return new ResourceExceptionDto(HttpStatus.BAD_REQUEST, "Body's request isn't correct");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResourceExceptionDto handleException(Exception e) {
        e.printStackTrace();
        return new ResourceExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
