package com.examination2.books.presentation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.Matchers.is;

import com.examination2.books.presentation.dto.BookInputDto;
import com.examination2.books.presentation.dto.BookUpdateDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class BookControllerTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    void ルートURLのアクセスを受け付ける() throws Exception {
        given()
            .when()
            .get("/")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Nested
    class 参照 {
        @Test
        void 全ての書籍情報を取得する() throws Exception {
            // assert
            given()
                .when()
                .get("/v1/books")
                .then()
                .statusCode(200)
                .body("books.size()", is(4))
                .body("books[0].id", is("1"))
                .body("books[0].title", is("テスト駆動開発"))
                .body("books[0].author", is("Kent Beck"))
                .body("books[0].publisher", is("オーム社"))
                .body("books[0].price", is(3080))

                .body("books[1].id", is("2"))
                .body("books[1].title", is("アジャイルサムライ"))
                .body("books[1].author", is("Jonathan Rasmusson"))
                .body("books[1].publisher", is("オーム社"))
                .body("books[1].price", is(2860))

                .body("books[2].id", is("3"))
                .body("books[2].title", is("エクストリームプログラミング"))
                .body("books[2].author", is("Kent Beck"))
                .body("books[2].publisher", is("オーム社"))
                .body("books[2].price", is(2420))

                .body("books[3].id", is("4"))
                .body("books[3].title", is("Clean Agile"))
                .body("books[3].author", is("Robert C. Martin"))
                .body("books[3].publisher", is("ドワンゴ"))
                .body("books[3].price", is(2640));
        }

        @Test
        void 指定したIDの従業員情報を取得する() {
            // setup
            String bookId = "1";

            // assert
            given()
                .when()
                .get("/v1/books/{id}", bookId)
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", is(bookId))
                .body("title", is("テスト駆動開発"))
                .body("author", is("Kent Beck"))
                .body("publisher", is("オーム社"))
                .body("price", is(3080));
        }
    }

    @Nested
    class 新規登録 {
        @Test
        void 指定した書籍情報を登録する() throws Exception {
            BookInputDto bookInputDto = new BookInputDto("達人プログラマー", "David Thomas", "オーム社", "3000");

            given()
                .contentType("application/json")
                .body(bookInputDto)
                .when()
                .post("/v1/books")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/v1/books/5"));
        }

        @ParameterizedTest(name = "{5}の場合")
        @CsvSource(delimiter = '|', textBlock= """
        # TITLE      | AUTHOR    | PUBLISHER | PRICE | MESSAGE                      | TESTNAME
              ''     | Kent Beck | オーム社    | 3080 | Title must not be blank      |  タイトルが空文字 
         テスト駆動開発 | ''        | オーム社   | 3080 | Author must not be blank      |  著者が空文字
         テスト駆動開発 | Kent Beck | ''        | 3080 | Publisher must not be blank  |  出版社が空文字
         テスト駆動開発 | Kent Beck | オーム社   | -100 | Price must be a numeric value | 価格がマイナス 
         テスト駆動開発 | Kent Beck | オーム社   | ABCD | Price must be a numeric value | 価格が数字ではない 
            """)
        void 指定した書籍情報が不正の場合エラーを返す(
            String title, String author, String publisher, String price, String message, String testName
        ) throws Exception {
            BookInputDto bookInputDto = new BookInputDto(title, author, publisher, price);
            given()
                .contentType("application/json")
                .body(bookInputDto)
                .when()
                .post("/v1/books")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("details[0]", is(message));
        }
    }

    @Nested
    class 更新 {
        @Test
            void 指定したIDの書籍情報を更新する() throws Exception {
            BookUpdateDto bookUpdateDto = new BookUpdateDto("テスト駆動開発第２版", "Kent Beck", "オーム社", "3080");
            given()
                .contentType("application/json")
                .body(bookUpdateDto)
                .when()
                .patch("/v1/books/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @ParameterizedTest(name = "{5}の場合")
        @CsvSource(delimiter = '|', textBlock= """
        # TITLE      | AUTHOR    | PUBLISHER | PRICE | MESSAGE                      | TESTNAME
              ''     | Kent Beck | オーム社    | 3080 | Title must not blank          |  タイトルが空文字 
         テスト駆動開発 | ''        | オーム社   | 3080 | Author must not blank         |  著者が空文字
         テスト駆動開発 | Kent Beck | ''        | 3080 | Publisher must not blank      |  出版社が空文字
         テスト駆動開発 | Kent Beck | オーム社   | -100 | Price must be a numeric value | 価格がマイナス 
         テスト駆動開発 | Kent Beck | オーム社   | ABCD | Price must be a numeric value | 価格が数字ではない 
            """)
        void 指定した書籍情報が不正の場合エラーを返す(
            String title,
            String author,
            String publisher,
            String price,
            String message,
            String testName
        ) throws Exception {
            BookUpdateDto bookUpdateDto = new BookUpdateDto(title, author, publisher, price);
            given()
                .contentType("application/json")
                .body(bookUpdateDto)
                .when()
                .patch("/v1/books/1")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("details[0]", is(message));
        }
    }

    @Nested
    class 削除 {
        @Test
        void 指定したIDの書籍情報を削除する() {
            given()
                .delete("/v1/books/{id}", "1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        }
    }

    @Test
    void 指定したIDの書籍情報が存在しない場合エラーを返す() {
        // setup
        String invalidBookId = "99";

        // assert
        given()
            .when()
            .get("/v1/books/{id}", invalidBookId)
            .then()
            .statusCode(400)
            .assertThat()
            .body("message", is("specified book [id = " + invalidBookId + "] is not found."));
    }
}
