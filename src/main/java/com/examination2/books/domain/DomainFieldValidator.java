package com.examination2.books.domain;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * ドメインオブジェクトのフィールドヴァリデーションメソッドを提供する。
 */
public class DomainFieldValidator {
    private DomainFieldValidator() {}

    /**
     * 文字列フィールドのヴァリデーションを実行する。
     *
     * @param title 項目名
     * @param value 対象の文字列
     */
    public static void validateStringField(String title, String value) {
        if(isEmpty(value)) throw new IllegalArgumentException(String.format("%s should not be empty or null. value: %s", title, value));
    }
}
