package com.examination2.books.presentation.validation;

import static java.util.Objects.isNull;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * データ更新時の入力内容(価格)のヴァリデーションロジックを提供するクラス。
 */
public class UpdatePriceValidator implements ConstraintValidator<ValidUpdatePrice, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (isNull(value)) {
            return true;
        }
        if (value.isBlank()) {
            return false;
        }
        try {
            int price = Integer.parseInt(value);
            return price > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
