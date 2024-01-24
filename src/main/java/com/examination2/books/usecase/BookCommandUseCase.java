package com.examination2.books.usecase;

import com.examination2.books.domain.Book;
import com.examination2.books.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCommandUseCase {
   private final BookRepository bookRepository;

   public Book registerBook(Book book) {
       return bookRepository.register(book);
   }

   public Book updateBook(Book book) { return bookRepository.update(book); }
}
