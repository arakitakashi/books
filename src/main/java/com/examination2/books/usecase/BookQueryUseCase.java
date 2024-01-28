package com.examination2.books.usecase;

import com.examination2.books.domain.book.Book;
import com.examination2.books.domain.book.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 書籍情報に関するクエリ処理を行うユースケースクラス。 書籍リポジトリを使用して、書籍情報の検索や取得を行います。
 */
@Service
@RequiredArgsConstructor
public class BookQueryUseCase {
    private final BookRepository bookRepository;

    /**
     * 全ての書籍情報を取得します。
     *
     * @return 書籍情報のリスト
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * 指定されたIDを持つ書籍情報を取得します。 書籍情報が存在しない場合は空のOptionalを返します。
     *
     * @param id 書籍ID
     * @return 指定されたIDの書籍情報を含むOptionalオブジェクト
     */
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }
}
