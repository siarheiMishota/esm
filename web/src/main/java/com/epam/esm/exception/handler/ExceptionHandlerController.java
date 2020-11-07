package com.epam.esm.exception.handler;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.handler.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
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
        return new ExceptionDto(e.getErrorMessage(), HttpStatus.BAD_REQUEST.value() + e.getCodeOfEntity().getCode());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionDto handleNotFoundId(ResourceNotFoundException e) {
        return new ExceptionDto(e.getErrorMessage(), HttpStatus.NOT_FOUND.value() + e.getCodeOfEntity().getCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ExceptionDto handleNumberFormatException(Exception e) {
        return new ExceptionDto("a", "a");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String exceptionMessage = new StringBuilder().append(e.getParameter().getParameterName())
            .append("=")
            .append(e.getValue().toString())
            .append(" ")
            .append("notCorrect").toString();
        return new ExceptionDto(exceptionMessage, HttpStatus.BAD_REQUEST.value() + CodeOfEntity.DEFAULT.getCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    public ExceptionDto handleHttpMessageConversionException(HttpMessageConversionException e) {
        return new ExceptionDto("", "");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ExceptionDto handleBindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String field = fieldError.getField();
        String incorrectValue = fieldError.getRejectedValue().toString();
        StringBuilder resultStringBuilder = new StringBuilder();
        resultStringBuilder.append(field).append("=")
            .append(incorrectValue).append(" ")
            .append("notCorrect");
        return new ExceptionDto(resultStringBuilder.toString(),
            HttpStatus.BAD_REQUEST.value() + CodeOfEntity.DEFAULT.getCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String field = fieldError.getField();
        String exceptionMessage = fieldError.getDefaultMessage();
        StringBuilder resultStringBuilder = new StringBuilder();
        resultStringBuilder.append(field).append(" - ")
            .append(exceptionMessage);
        return new ExceptionDto(resultStringBuilder.toString(),
            HttpStatus.BAD_REQUEST.value() + CodeOfEntity.DEFAULT.getCode());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionDto handleException(Exception e) {
        return new ExceptionDto("internal server error", "50000");
    }
}
