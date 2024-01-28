package com.examination2.books.presentation.validation;

import static org.apache.commons.lang3.StringUtils.isBlank;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * priceのヴァリデーションロジックを提供するクラス。
 */
public class PriceValidator implements ConstraintValidator<ValidPrice, String> {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
       if (isBlank(value)) {
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
