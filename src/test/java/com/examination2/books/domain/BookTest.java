package com.examination2.books.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BookTest {
    @ParameterizedTest(name = "{5}の場合")
    @CsvSource(delimiter = '|', textBlock = """
        # TITLE      | AUTHOR    | PUBLISHER | PRICE | MESSAGE                                           | TESTNAME
                     | Kent Beck | オーム社    | 3080 | Title should not be empty or null. value: null    |  タイトルがnull 
              ''     | Kent Beck | オーム社    | 3080 | Title should not be empty or null. value:         |  タイトルが空文字 
         テスト駆動開発 |           | オーム社   | 3080 | Author should not be empty or null. value: null    |  著者がnull
         テスト駆動開発 | ''        | オーム社   | 3080 | Author should not be empty or null. value:         |  著者が空文字
         テスト駆動開発 | Kent Beck |           | 3080 | Publisher should not be empty or null. value: null |  出版社がnull
         テスト駆動開発 | Kent Beck | ''        | 3080 | Publisher should not be empty or null. value:      |  出版社が空文字
         テスト駆動開発 | Kent Beck | オーム社   | -100 | Price should be positive numeric value             | 価格がマイナス 
         テスト駆動開発 | Kent Beck | オーム社   | ABCD | Price should be positive numeric value             | 価格が数字ではない 
         
         """)
    void ガード条件に違反する場合(String title, String author, String publisher, String price,
        String message, String testName) {

        // assert
        String id = "id";
        assertThatThrownBy(() -> Book.create(id, title, author, publisher, price))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(message);
    }
}