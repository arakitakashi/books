package com.examination2.books.usecase;

import com.examination2.books.domain.Book;
import com.examination2.books.domain.BookRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookQueryUseCase {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }
}
