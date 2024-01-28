package com.examination2.books.presentation;

import static java.util.Objects.isNull;

import com.examination2.books.domain.Book;
import com.examination2.books.presentation.dto.BookDto;
import com.examination2.books.presentation.dto.BookInputDto;
import com.examination2.books.presentation.dto.BookListDto;
import com.examination2.books.presentation.dto.BookUpdateDto;
import com.examination2.books.usecase.BookCommandUseCase;
import com.examination2.books.usecase.BookQueryUseCase;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookQueryUseCase bookQueryUseCase;
    private final BookCommandUseCase bookCommandUseCase;

    @GetMapping("/")
    public ResponseEntity<Void> getRoot() {
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/v1/books")
    @ResponseStatus(HttpStatus.OK)
    public BookListDto getBooks() {
        List<BookDto> bookDtos = bookQueryUseCase.findAll().stream().map(this::convertToDto)
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
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/v1/books")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createBook(@Validated @RequestBody BookInputDto bookInputDto) {
        Book newBook = bookCommandUseCase.registerBook(
            Book.createWithoutId(bookInputDto.title(), bookInputDto.author(),
                bookInputDto.publisher(), bookInputDto.price()));
        URI location = getLocation(newBook);
        return ResponseEntity.created(location).build();
    }

    private URI getLocation(Book newBook) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
            .buildAndExpand(newBook.getId()).toUri();
    }

    @PatchMapping("/v1/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateBook(@PathVariable String id,
        @RequestBody @Validated BookUpdateDto bookUpdateDto) {
        bookQueryUseCase.findById(id).ifPresent(existingBook -> {
            bookCommandUseCase.updateBook(createUpdateBook(bookUpdateDto, existingBook));
        });
        return ResponseEntity.noContent().build();
    }

    private Book createUpdateBook(BookUpdateDto bookUpdateDto, Book existingBook) {
        String updateTitle =
            !isNull(bookUpdateDto.title()) ? bookUpdateDto.title() : existingBook.getTitle();
        String updateAuthor =
            !isNull(bookUpdateDto.author()) ? bookUpdateDto.author() : existingBook.getAuthor();
        String updatePublisher = !isNull(bookUpdateDto.publisher()) ? bookUpdateDto.publisher()
            : existingBook.getPublisher();
        String updatePrice =
            !isNull(bookUpdateDto.price()) ? bookUpdateDto.price() : existingBook.getPrice();

        return Book.create(existingBook.getId(), updateTitle, updateAuthor, updatePublisher,
            updatePrice);
    }

    @DeleteMapping("/v1/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteEmployees(@PathVariable String id) {
        bookCommandUseCase.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
