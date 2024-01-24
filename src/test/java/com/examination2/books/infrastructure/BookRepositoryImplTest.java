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

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
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
            List<Book> all = sut.findAll();
            all.forEach(book -> System.out.println(book.toString()));
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
                .hasMessage("specified book [id = 99] is not found.");
        }
    }

    @Nested
    class 新規登録 {
        @Test
        void 指定した書籍情報を新規登録する() {
            // setup
            Book newBook = Book.createWithoutId("達人プログラマー", "David Thomas", "オーム社", "4000");

            Optional<Book> expected = Optional.of(Book.create("5", "達人プログラマー", "David Thomas", "オーム社", "4000"));

            // execute
            sut.register(newBook);

            // assert
            Optional<Book> actual = sut.findById("5");
            assertThat(actual).isEqualTo(expected);

        }
    }

    @Nested
    class 更新 {
        @Test
        void 指定した書籍情報を更新する() {
            // setup
            Optional<Book> expected = Optional.of(Book.create("1", "テスト駆動開発第２版", "Kent Beck", "オーム社", "3080"));
            Book book = Book.create("1", "テスト駆動開発第２版", "Kent Beck", "オーム社", "3080");

            // execute
            sut.update(book);

            // assert
            Optional<Book> actual = sut.findById("1");
            assertThat(actual).isEqualTo(expected);
        }
    }
}
