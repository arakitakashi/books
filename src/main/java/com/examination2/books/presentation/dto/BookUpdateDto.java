package com.examination2.books.presentation.dto;

import com.examination2.books.presentation.validation.ValidPrice;
import jakarta.validation.constraints.NotBlank;

public record BookUpdateDto(
    @NotBlank(message = "Title must not blank")
    String title,

    @NotBlank(message = "Author must not blank")
    String author,

    @NotBlank(message = "Publisher must not blank")
    String publisher,

    @ValidPrice(message = "Price must be a numeric value")
    String price) {
}
