package com.examination2.books.domain;

import static org.apache.commons.lang3.StringUtils.*;

public class DomainFieldValidator {
    private DomainFieldValidator() {}
    public static void validateStringField(String title, String value) {
        if(isEmpty(value)) throw new IllegalArgumentException(String.format("%s should not be empty or null. value: %s", title, value));
    }
}
