package com.examination2.books.presentation.dto;

import java.util.List;

/**
 * 書籍情報のリストを表すデータ転送オブジェクト（DTO）。 複数の{@link BookDto}オブジェクトを含むリストを保持し、
 * プレゼンテーション層で書籍情報の集合を転送する際に使用されます。
 */
public record BookListDto(List<BookDto> books) {
}
