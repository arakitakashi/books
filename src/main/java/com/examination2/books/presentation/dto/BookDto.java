package com.examination2.books.presentation.dto;

/**
 * 書籍のデータ転送オブジェクト（DTO）。 プレゼンテーション層での従業員データの転送に使用されます。
 */
public record BookDto(String id, String title, String author, String publisher, int price) {

}
