package app.core.errorhandling;

import app.core.errorhandling.exceptions.UserAlreadyExistsException;
import app.core.errorhandling.model.CommonExceptionJson;
import app.core.errorhandling.model.ValidationExceptionJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionJson> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String,String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));
        log.error("MethodArgumentNotValidException: {}.", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ValidationExceptionJson.builder().msg("Validation failed").errors(errors).build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonExceptionJson> badCredentialsExceptionHandler(BadCredentialsException e) {
        log.error("BadCredentialsException: {}.", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommonExceptionJson.builder().msg("Invalid username or password").cause(e.getMessage()).build());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<CommonExceptionJson> insufficientAuthenticationExceptionHandler(InsufficientAuthenticationException e) {
        log.error("InsufficientAuthenticationException: {}.", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommonExceptionJson.builder().msg("Insufficient authentication").cause(e.getMessage()).build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CommonExceptionJson> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException: {}.", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(CommonExceptionJson.builder().msg("Data violation on database").cause(e.getMessage()).build());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CommonExceptionJson> userAlreadyExistsExceptionHandler(UserAlreadyExistsException e) {
        log.error("UserAlreadyExistsException: {}.", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(CommonExceptionJson.builder().msg("User already exists").cause(e.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonExceptionJson> unhandledExceptionHandler(Exception e) {
        log.error("An unexpected error occurs: {}.", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonExceptionJson.builder().msg("Something went wrong").cause(e.getMessage()).build());
    }
}
