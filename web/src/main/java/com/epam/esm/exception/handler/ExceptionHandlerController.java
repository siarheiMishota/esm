package com.epam.esm.exception.handler;

import com.epam.esm.entity.CodeOfEntity;
import com.epam.esm.exception.EntityDuplicateException;
import com.epam.esm.exception.EntityIntegrityViolationException;
import com.epam.esm.exception.ResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.handler.dto.ExceptionDto;
import io.jsonwebtoken.JwtException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String exceptionMessage = new StringBuilder().append(e.getParameter().getParameterName())
            .append("=")
            .append(e.getValue())
            .append(" ")
            .append("notCorrect").toString();
        return new ExceptionDto(exceptionMessage, HttpStatus.BAD_REQUEST.value() + CodeOfEntity.DEFAULT.getCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ExceptionDto handleBindException(BindException e) {
        String messageError = getMessageFromBindingResult(e.getBindingResult());

        return new ExceptionDto(messageError + " not correct",
            HttpStatus.BAD_REQUEST.value() + CodeOfEntity.DEFAULT.getCode());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String messageError = getMessageFromBindingResult(e.getBindingResult());

        return new ExceptionDto(messageError + " not correct",
            HttpStatus.BAD_REQUEST.value() + CodeOfEntity.DEFAULT.getCode());
    }

    private String getMessageFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + " = " + fieldError.getRejectedValue())
            .collect(Collectors.joining(" and ", "(", ")"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionDto handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ExceptionDto("Incorrect value for numeric field",
            HttpStatus.BAD_REQUEST.value() + CodeOfEntity.DEFAULT.getCode());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityDuplicateException.class)
    public ExceptionDto handleEntityDuplicateException(EntityDuplicateException e) {
        return new ExceptionDto(e.getMessage(),
            HttpStatus.CONFLICT.value() + e.getCodeOfEntity().getCode());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(EntityIntegrityViolationException.class)
    public ExceptionDto handleEntityIntegrityViolationException(EntityIntegrityViolationException e) {
        return new ExceptionDto(e.getMessage(),
            HttpStatus.UNPROCESSABLE_ENTITY.value() + e.getCodeOfEntity().getCode());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionDto handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new ExceptionDto(e.getMessage(),
            HttpStatus.METHOD_NOT_ALLOWED.value() + CodeOfEntity.DEFAULT.getCode());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ExceptionDto handleAccessDeniedException(AccessDeniedException e) {
        return new ExceptionDto("No rights",
            HttpStatus.FORBIDDEN.value() + CodeOfEntity.DEFAULT.getCode());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ExceptionDto handleBadClientCredentialsException(BadCredentialsException e) {
        return new ExceptionDto("Unauthorized",
            HttpStatus.UNAUTHORIZED.value() + CodeOfEntity.DEFAULT.getCode());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtException.class)
    public ExceptionDto handleJwtException(JwtException e) {
        return new ExceptionDto("Unauthorized",
            HttpStatus.UNAUTHORIZED.value() + CodeOfEntity.DEFAULT.getCode());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionDto handleException(Exception e) {
        return new ExceptionDto("internal server error",
            HttpStatus.INTERNAL_SERVER_ERROR.value() + CodeOfEntity.DEFAULT.getCode());
    }
}
