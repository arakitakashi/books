package com.examination2.books.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BookTest {
    @ParameterizedTest(name = "{3}の場合")
    @CsvSource(delimiter = '|', textBlock = """
        # TITLE | AUTHOR | PUBLISHER | PRICE | MESSAGE | TESTNAME
                | Kent Beck | オーム社 | 3080 | Title should not be null|  タイトルがnull 
         """)
    void ガード条件に違反する場合(String title, String author, String publisher, int price,
        String message, String testName) {
        // assert
        String id = "id";
        assertThatThrownBy(() -> Book.create(id, title, author, publisher, price))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(message);
    }
}