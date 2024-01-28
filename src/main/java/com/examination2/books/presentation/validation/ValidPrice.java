package com.examination2.books.presentation.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @ValidPrice アノテーションを定義するクラス。
 */
@Documented
@Constraint(validatedBy = PriceValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValidPrice {
    String message() default "Invalid price value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
