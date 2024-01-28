package com.examination2.books.domain.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 書籍情報の例外をグローバルに処理するクラス。 アプリケーション全体で発生する特定の例外に対して、共通のレスポンス形式を提供します。
 */
@ControllerAdvice
public class BookGlobalExceptionHandler {
    private static final String KEY_OF_CODE = "code";
    private static final String KEY_OF_MESSAGE = "message";
    private static final String KEY_OF_DETAILS = "details";

    /**
     * {@link BookNotFoundException}が発生した場合の処理を行います。 クライアントにはBadRequestと共にエラー情報を返します。
     *
     * @param e 発生した{@link BookNotFoundException}
     * @return エラー情報を含むレスポンスエンティティ
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookNotFoundException(
        BookNotFoundException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put(KEY_OF_CODE, "0003");
        body.put(KEY_OF_MESSAGE, e.getMessage());
        body.put(KEY_OF_DETAILS, new ArrayList<>());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * 登録の際の入力値が不正な場合の例外（{@link MethodArgumentNotValidException}）を処理します。 バリデーションエラーの詳細をクライアントに返します。
     *
     * @param e 発生した{@link MethodArgumentNotValidException}
     * @return バリデーションエラー情報を含むレスポンスエンティティ
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
        MethodArgumentNotValidException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "0002");
        body.put(KEY_OF_MESSAGE, "request validation error is occurred.");

        List<String> details = e.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        body.put(KEY_OF_DETAILS, details);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * 入力値が不正の場合の例外（{@link IllegalArgumentException}）を処理します。 バリデーションエラーの詳細をクライアントに返します。
     *
     * @param e 発生した{@link IllegalArgumentException}
     * @return バリデーションエラー情報を含むレスポンスエンティティ
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
        IllegalArgumentException e) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(KEY_OF_CODE, "0002");
        body.put(KEY_OF_MESSAGE, "request validation error is occurred");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
