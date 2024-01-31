package com.examination2.books.presentation.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * {@code @ValidUpdatePrice} アノテーションは、更新時の入力内容の妥当性を検証するために使用されます。
 * このアノテーションは、フィールドレベルで使用され、指定された入力内容が有効であるかどうかを検証します。
 */
@Documented
@Constraint(validatedBy = UpdatePriceValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface ValidUpdatePrice {

    /**
     * アノテーションの失敗時に使用されるデフォルトメッセージを提供します。
     *
     * @return デフォルトのエラーメッセージ
     */
    String message() default "Invalid update price value";

    /**
     * バリデーションのグループを指定するために使用されます。
     *
     * @return バリデーションのグループ
     */
    Class<?>[] groups() default {};

    /**
     * ペイロードを使用して、バリデーションのメタデータや追加の情報を提供します。
     *
     * @return バリデーションのペイロード
     */
    Class<? extends Payload>[] payload() default {};
}
