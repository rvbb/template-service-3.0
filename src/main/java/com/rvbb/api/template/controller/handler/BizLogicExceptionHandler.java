package com.rvbb.api.template.controller.handler;

import com.rvbb.api.template.dto.Response;
import com.rvbb.api.template.exception.BizLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class BizLogicExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  @NonNull
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
    String message = ex.getParameterName() + " parameter is missing";
    return buildResponseEntity(BAD_REQUEST, Error.builder().message(message).code(BAD_REQUEST.value()).build());
  }

  @Override
  @NonNull
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
    return buildResponseEntity(UNSUPPORTED_MEDIA_TYPE,
            Error.builder().message(builder.substring(0, builder.length() - 2)).code(UNSUPPORTED_MEDIA_TYPE.value()).build());
  }

  @Override
  @NonNull
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {

    Error apiError = Error.builder().message("Validation error").code(BAD_REQUEST.value()).build();
    apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
    apiError.addValidationError(ex.getBindingResult().getGlobalErrors());

    return buildResponseEntity(BAD_REQUEST, apiError);
  }

  @ExceptionHandler(javax.validation.ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(
      javax.validation.ConstraintViolationException ex) {
    Error apiError = Error.builder().message("Validation error").code(BAD_REQUEST.value()).build();
    apiError.addValidationErrors(ex.getConstraintViolations());
    return buildResponseEntity(BAD_REQUEST, apiError);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
    Error error = Error.builder().message(ex.getMessage()).code(NOT_FOUND.value()).build();
    return buildResponseEntity(NOT_FOUND, error);
  }

  @Override
  @NonNull
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      @NonNull HttpMessageNotReadableException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    ServletWebRequest servletWebRequest = (ServletWebRequest) request;
    log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
    String message = "Malformed JSON request";
    return buildResponseEntity(BAD_REQUEST, Error.builder().message(message).code(BAD_REQUEST.value()).build());
  }
  
  @Override
  @NonNull
  protected ResponseEntity<Object> handleHttpMessageNotWritable(
      @NonNull HttpMessageNotWritableException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    String message = "Error writing JSON output";
    return buildResponseEntity(INTERNAL_SERVER_ERROR, Error.builder().message(message).code(INTERNAL_SERVER_ERROR.value()).build());
  }

  @Override
  @NonNull
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      @NonNull NoHandlerFoundException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    String message = String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL());
    Error error = Error.builder().message(message).code(BAD_REQUEST.value()).build();
    return buildResponseEntity(BAD_REQUEST, error);
  }
  @ExceptionHandler(DataIntegrityViolationException.class)
  protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    if (ex.getCause() instanceof ConstraintViolationException) {
      return buildResponseEntity(CONFLICT, Error.builder().message("Database error").code(CONFLICT.value()).build());
    }
    return buildResponseEntity(INTERNAL_SERVER_ERROR, Error.builder().message("Internal Server Error").code( INTERNAL_SERVER_ERROR.value()).build());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String message = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
            ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
    Error error = Error.builder().message(message).code(BAD_REQUEST.value()).build();
    return buildResponseEntity(BAD_REQUEST, error);
  }

  @ExceptionHandler(BizLogicException.class)
  protected ResponseEntity<Object> handleBusinessService(BizLogicException ex) {
    Error error = Error.builder().message(ex.getMessage()).code(ex.getCode()).build();
    return buildResponseEntity(CONFLICT, error);
  }

  @ExceptionHandler
  protected ResponseEntity<Object> handleInternalException(Exception ex) {
    Error error = Error.builder().message(ex.getMessage()).code( INTERNAL_SERVER_ERROR.value()).build();
    return buildResponseEntity(INTERNAL_SERVER_ERROR, error);
  }

  private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, Error error) {
    return new ResponseEntity<>(Response.fail(error), httpStatus);
  }
}