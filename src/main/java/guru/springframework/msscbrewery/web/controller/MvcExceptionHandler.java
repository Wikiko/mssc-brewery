package guru.springframework.msscbrewery.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>(ex.getConstraintViolations().size());

        ex.getConstraintViolations().forEach(
                constraintViolation -> errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<String>> bindExceptionHandler(BindException ex) {
        List<String> errors = new ArrayList<>(ex.getBindingResult().getFieldErrors().size());

        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> errors.add(fieldError.getField() + " : " + fieldError.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }
}
