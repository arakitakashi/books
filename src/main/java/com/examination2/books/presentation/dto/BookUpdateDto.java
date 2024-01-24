package com.examination2.books.presentation.dto;

import com.examination2.books.presentation.validation.ValidPrice;
import jakarta.validation.constraints.NotBlank;

public record BookUpdateDto(
    @NotBlank(message = "title must not blank")
    String title,

    @NotBlank(message = "author must not blank")
    String author,

    @NotBlank(message = "publisher must not blank")
    String publisher,

    @ValidPrice(message = "price must be a numeric value")
    String price) {
}
