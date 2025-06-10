package app.core.errorhandling;

import app.core.errorhandling.model.CommonException;
import app.core.errorhandling.model.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<ValidationException> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String,String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));
        log.error("MethodArgumentNotValidException: {}.", errors, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ValidationException.builder().msg("Validation failed").errors(errors).build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonException> badCredentialsExceptionHandler(BadCredentialsException e) {
        log.error("BadCredentialsException: {}.", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommonException.builder().msg("Invalid username or password").cause(e.getMessage()).build());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<CommonException> insufficientAuthenticationExceptionHandler(InsufficientAuthenticationException e) {
        log.error("InsufficientAuthenticationException: {}.", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommonException.builder().msg("Insufficient authentication").cause(e.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonException> unhandledExceptionHandler(Exception e) {
        log.error("An unexpected error occurs: {}.", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonException.builder().msg("Something went wrong").cause(e.getMessage()).build());
    }
}
