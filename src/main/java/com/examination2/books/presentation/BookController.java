package com.examination2.books.presentation;


import com.examination2.books.domain.Book;
import com.examination2.books.presentation.dto.BookDto;
import com.examination2.books.presentation.dto.BookListDto;
import com.examination2.books.usecase.BookQueryUseCase;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookController {
   private final BookQueryUseCase bookQueryUseCase;

   @GetMapping("/v1/books")
    @ResponseStatus(HttpStatus.OK)
    public BookListDto getBooks() {
       List<BookDto> bookDtos = bookQueryUseCase.findAll().stream()
           .map(this::convertToDto)
           .toList();

       return new BookListDto(bookDtos);
   }

   private BookDto convertToDto(Book book) {
   return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(),
       book.getPrice());
   }

   @GetMapping("/v1/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
       Optional<Book> book = bookQueryUseCase.findById(id);
       return book.map(ResponseEntity::ok)
           .orElseGet(() -> ResponseEntity.notFound().build());
   }
}