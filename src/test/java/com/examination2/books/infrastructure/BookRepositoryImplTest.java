package com.examination2.books.infrastructure;

import com.examination2.books.domain.Book;
import com.examination2.books.domain.BookRepository;
import com.examination2.books.domain.exception.BookNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@ActiveProfiles("dev")
class BookRepositoryImplTest {
    @Autowired
    BookRepository sut;

    @Test
    void 全ての書籍情報を取得する() {
        // setup
        Book book1 = Book.create("1", "テスト駆動開発", "Kent Beck", "オーム社", "3080");
        Book book2 = Book.create("2", "アジャイルサムライ", "Jonathan Rasmusson", "オーム社", "2860");
        Book book3 = Book.create("3", "エクストリームプログラミング", "Kent Beck", "オーム社", "2420");
        Book book4 = Book.create("4", "Clean Agile", "Robert C. Martin", "ドワンゴ", "2640");

        List<Book> expected = List.of(book1, book2, book3, book4);

        // execute
        List<Book> actual = sut.findAll();

        // assert
        assertThat(actual).isEqualTo(expected);
    }

    @Nested
    class 単体取得 {
        @Test
        void 指定したIDの書籍情報を取得する() {
            // setup
            Book book1 = Book.create("1", "テスト駆動開発", "Kent Beck", "オーム社", "3080");

            Optional<Book> expected = Optional.of(book1);

            // execute
            Optional<Book> actual = sut.findById("1");

            // assert
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 指定したIDの書籍情報が存在しない場合例外を返す() {
            // execute
            assertThatThrownBy(() -> sut.findById("99"))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessage("specified employee [id = 99] is not found.");
        }
    }
}
