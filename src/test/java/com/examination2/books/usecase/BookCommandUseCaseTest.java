package com.examination2.books.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import com.examination2.books.domain.Book;
import com.examination2.books.domain.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DirtiesContext(classMode= ClassMode.BEFORE_CLASS)
public class BookCommandUseCaseTest {
    @Autowired
    BookCommandUseCase sut;

    @Autowired
    BookRepository bookRepository;

    @Test
    void 指定した書籍情報を新規登録する() {
        // setup
        Book newBook = Book.createWithoutId("達人プログラマー", "David Thomas", "オーム社", "4000");

        Book expected = Book.create("5", "達人プログラマー", "David Thomas", "オーム社", "4000");

        // execute
        Book actual = sut.registerBook(newBook);

        // assert
        assertThat(actual).isEqualTo(expected);
    }

}
