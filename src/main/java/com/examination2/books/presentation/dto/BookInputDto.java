package com.examination2.books.presentation.dto;

import com.examination2.books.presentation.validation.ValidPrice;
import jakarta.validation.constraints.NotBlank;

/**
 * 書籍情報の登録または更新時に使用されるデータ転送オブジェクト（DTO）。
 * このDTOは主にプレゼンテーション層での従業員データの入力に使用されます。
 */
public record BookInputDto(
    @NotBlank(message = "Title must not be blank")
    String title,

    @NotBlank(message = "Author must not be blank")
    String author,

    @NotBlank(message = "Publisher must not be blank")
    String publisher,

    @ValidPrice(message = "Price must be a numeric value")
    String price) {
}
