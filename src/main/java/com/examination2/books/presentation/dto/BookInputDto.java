package com.examination2.books.presentation.dto;

import static org.apache.commons.lang3.StringUtils.isBlank;

public record BookInputDto(String title, String author, String publisher, String price) {
    public BookInputDto {
        if(isBlank(title)) throw new IllegalArgumentException("title must not be blank");
        if(isBlank(author)) throw new IllegalArgumentException("author must not be blank");
        if(isBlank(publisher)) throw new IllegalArgumentException("publisher must not be blank");
        if(isBlank(price)) throw new IllegalArgumentException("price must not be blank");
    }
}
