package com.examination2.books.presentation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * データ更新時の入力内容のヴァリデーションロジックを提供するクラス。
 */
public class UpdateStringValidator implements ConstraintValidator<ValidUpdateString, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return (value == null) || (!value.isBlank());
    }
}
