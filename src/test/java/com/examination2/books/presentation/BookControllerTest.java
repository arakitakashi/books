package com.examination2.books.presentation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class BookControllerTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
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
                .body("books[0].price", is("3080"))

                .body("books[1].id", is("2"))
                .body("books[1].title", is("アジャイルサムライ"))
                .body("books[1].author", is("Jonathan Rasmusson"))
                .body("books[1].publisher", is("オーム社"))
                .body("books[1].price", is("2860"))

                .body("books[2].id", is("3"))
                .body("books[2].title", is("エクストリームプログラミング"))
                .body("books[2].author", is("Kent Beck"))
                .body("books[2].publisher", is("オーム社"))
                .body("books[2].price", is("2420"))

                .body("books[3].id", is("4"))
                .body("books[3].title", is("Clean Agile"))
                .body("books[3].author", is("Robert C. Martin"))
                .body("books[3].publisher", is("ドワンゴ"))
                .body("books[3].price", is("2640"));
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
                .body("price", is("3080"));
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
}
