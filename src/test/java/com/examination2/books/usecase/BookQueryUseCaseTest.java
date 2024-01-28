package com.examination2.books.usecase;

import com.examination2.books.domain.Book;
import com.examination2.books.domain.BookRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookQueryUseCaseTest {
    @Autowired
    BookQueryUseCase sut;

    @Autowired
    BookRepository bookRepository;

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

    @Test
    void 指定したIDの従業員情報を取得する() {
        // setup
        Book book = Book.create("1", "テスト駆動開発", "Kent Beck", "オーム社", "3080");
        Optional<Book> expected = Optional.of(book);

        // execute
        Optional<Book> actual = sut.findById("1");

        // assert
        assertThat(actual).isEqualTo(expected);
    }
}
