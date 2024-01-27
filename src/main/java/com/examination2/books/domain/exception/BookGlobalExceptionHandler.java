package com.examination2.books.domain.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.coyote.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookGlobalExceptionHandler {
    private static final String KEY_OF_CODE = "code";
    private static final String KEY_OF_MESSAGE = "message";
    private static final String KEY_OF_DETAILS = "details";

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookNotFoundException(
        BookNotFoundException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put(KEY_OF_CODE, "0003");
        body.put(KEY_OF_MESSAGE, e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
        MethodArgumentNotValidException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "0002");
        body.put(KEY_OF_MESSAGE, "request validation error is occured.");

        List<String> details = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();
        System.out.println(details);
        body.put(KEY_OF_DETAILS, details);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
        IllegalArgumentException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "0002");
        body.put(KEY_OF_MESSAGE, "request validation error is occurred");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
