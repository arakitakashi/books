package com.examination2.books.infrastructure;

import com.examination2.books.domain.Book;
import com.examination2.books.domain.BookRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @Override
    public List<Book> findAll() {
        return null;
    }
}
