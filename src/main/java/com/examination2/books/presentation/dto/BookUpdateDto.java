package com.examination2.books.presentation.dto;

import com.examination2.books.presentation.validation.ValidUpdatePrice;
import com.examination2.books.presentation.validation.ValidUpdateString;

/**
 * 書籍情報の更新時に使用されるデータ転送オブジェクト（DTO）。 プレゼンテーション層での書籍情報データの更新処理に使用されます。
 */
public record BookUpdateDto(
    @ValidUpdateString(message = "Title must not blank")
    String title,

    @ValidUpdateString(message = "Author must not blank")
    String author,

    @ValidUpdateString(message = "Publisher must not blank")
    String publisher,

    @ValidUpdatePrice(message = "Price must be a numeric value")
    String price
) {

}
