package com.examination2.books.usecase;

import com.examination2.books.domain.book.Book;
import com.examination2.books.domain.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 書籍情報の登録、更新、削除を行うユースケースクラス。 書籍リポジトリを利用して、これらの操作を行います。
 */
@Service
@RequiredArgsConstructor
public class BookCommandUseCase {

    private final BookRepository bookRepository;

    /**
     * 新しい書籍情報を登録します。 登録された書籍情報を返します。
     *
     * @param book 登録する書籍の情報
     * @return 登録された書籍
     */
    public Book registerBook(Book book) {
        return bookRepository.register(book);
    }

    /**
     * 既存の書籍情報を更新します。 更新された書籍情報を返します。
     *
     * @param book 更新する書籍の情報
     * @return 更新された書籍
     */
    public Book updateBook(Book book) {
        return bookRepository.update(book);
    }

    /**
     * 指定されたIDの書籍情報を削除します。 削除が成功した場合はtrue、失敗した場合はfalseを返します。
     *
     * @param id 削除する書籍情報のID
     * @return 削除の成否
     */
    public boolean deleteBook(String id) {
        return bookRepository.delete(id);
    }
}
