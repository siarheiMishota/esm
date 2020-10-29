package com.epam.esm.exception.handler;

import com.epam.esm.exception.NotFoundIdException;
import com.epam.esm.exception.handler.dto.ExceptionDto;
import com.epam.esm.exception.handler.dto.NotFoundExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundIdException.class)
    public NotFoundExceptionDto handleNotFoundId(NotFoundIdException e) {
        return new NotFoundExceptionDto(String.format("Requested resource not found (%s=%d)", e.getNameField(), e.getValue()), e.getHttpStatus().value());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({NumberFormatException.class, MethodArgumentTypeMismatchException.class})
    public NotFoundExceptionDto handleNumberFormatException(NumberFormatException e) {
        return new NotFoundExceptionDto(String.format("Requested resource isn't correct (id=%s)", e.getMessage()), 404);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(ConstraintViolationException.class)
    public NotFoundExceptionDto handleConstrainValidationException(ConstraintViolationException exception) {
        return new NotFoundExceptionDto(exception.getMessage(), 404);

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionDto handleException(Exception e) {
        return new ExceptionDto(e.getMessage());
    }
}
