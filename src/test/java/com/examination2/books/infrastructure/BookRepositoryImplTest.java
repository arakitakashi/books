package com.examination2.books.infrastructure;

import com.examination2.books.domain.Book;
import com.examination2.books.domain.BookRepository;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import java.sql.DriverManager;
import java.util.List;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DBRider
@DBUnit
@ActiveProfiles("dev")
class BookRepositoryImplTest {
    private static final String DB_URL = "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "password";

    private static final ConnectionHolder connectionHolder = () -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

    @Autowired
    BookRepository sut;

    @BeforeAll
    static void setUpAll() {
        Flyway.configure().dataSource(DB_URL, DB_USER, DB_PASSWORD).load().migrate();
    }
    @Test
    @DataSet(value = "datasets/setup/books.yml")
    @ExpectedDataSet(value = "datasets/expected/books.yml")
    void 全ての書籍情報を取得する() {
        // setup
        Book book1 = Book.create("1", "title1", "yamada taro", "example company", "1000");
        Book book2 = Book.create("2", "title2", "tanaka taro", "example company", "2000");

        List<Book> expected = List.of(book1, book2);

        // execute
        List<Book> actual = sut.findAll();

        // assert
        assertThat(actual).isEqualTo(expected);
    }
}
