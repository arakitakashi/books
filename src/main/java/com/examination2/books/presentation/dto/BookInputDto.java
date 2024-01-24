package com.examination2.books.presentation.dto;

import com.examination2.books.presentation.validation.ValidPrice;
import jakarta.validation.constraints.NotBlank;

public record BookInputDto(
    @NotBlank(message = "title must not be blank")
    String title,

    @NotBlank(message = "author must not be blank")
    String author,

    @NotBlank(message = "publisher must not be blank")
    String publisher,

    @ValidPrice(message = "price must be a numeric value")
    String price) {
    public BookInputDto {
    }
}
