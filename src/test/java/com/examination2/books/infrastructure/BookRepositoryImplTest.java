package com.examination2.books.infrastructure;

import com.examination2.books.domain.Book;
import com.examination2.books.domain.BookRepository;
import java.util.List;
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
}
