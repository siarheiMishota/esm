package com.epam.esm.exception.handler;

import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.handler.dto.ExceptionDto;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceException.class)
    public ExceptionDto handleNotFoundId(ResourceException e) {
        return new ExceptionDto(e.getHttpStatus(), e.getErrorMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NumberFormatException.class, MethodArgumentTypeMismatchException.class,
        ConstraintViolationException.class})
    public ExceptionDto handleNumberFormatException(Exception e) {
        return new ExceptionDto(HttpStatus.BAD_REQUEST, "Id isn't correct");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    public ExceptionDto handleHttpMessageConversionException(HttpMessageConversionException e) {
        return new ExceptionDto(HttpStatus.BAD_REQUEST, "Body's request isn't correct");
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ExceptionDto handleBindException(Exception e) {
        return new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionDto handleException(Exception e) {
        return new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
