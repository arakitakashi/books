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

/**
 * 書籍に関する操作を提供するコントローラー。 このクラスは従業員の検索、登録、更新、削除のためのエンドポイントを提供します。
 */
@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookQueryUseCase bookQueryUseCase;
    private final BookCommandUseCase bookCommandUseCase;

    /**
     * アプリケーションのルートエンドポイントに対するレスポンスを提供します。
     *
     * @return HTTPステータス200（OK）のレスポンスエンティティ
     */
    @GetMapping("/")
    public ResponseEntity<Void> getRoot() {
        return ResponseEntity.ok().build();
    }

    /**
     * すべての書籍情報を取得します。
     *
     * @return 書籍のDTOを含むリスト
     */
    @GetMapping("/v1/books")
    @ResponseStatus(HttpStatus.OK)
    public BookListDto getBooks() {
        List<BookDto> bookDtos = bookQueryUseCase.findAll().stream().map(this::convertToDto)
            .toList();

        return new BookListDto(bookDtos);
    }

    private BookDto convertToDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(),
            Integer.parseInt(book.getPrice()));
    }

    /**
     * 指定されたIDの書籍情報を取得します。 存在しない場合は404 Not Foundを返します。
     *
     * @param id 　書籍ID
     * @return 書籍情報のレスポンスエンティティ
     */
    @GetMapping("/v1/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookDto> getBookById(@PathVariable String id) {
        Optional<Book> book = bookQueryUseCase.findById(id);
        return book.map(b -> ResponseEntity.ok(convertToDto(b)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 新しい書籍情報を登録します。 登録に成功すると201 Createdとともに従業員のURIを返します。
     *
     * @param bookInputDto 新規書籍情報のDTO
     * @return 作成された書籍情報のURIを含むレスポンスエンティティ
     */
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

    /**
     * 指定されたIDの書籍情報を更新します。 更新に成功すると204 No Contentを返します。
     *
     * @param id            書籍ID
     * @param bookUpdateDto 更新する書籍の情報
     * @return レスポンスエンティティ
     */
    @PatchMapping("/v1/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateBook(@PathVariable String id,
        @RequestBody @Validated BookUpdateDto bookUpdateDto) {
        bookQueryUseCase.findById(id).ifPresent(existingBook -> bookCommandUseCase.updateBook(
            createUpdateBook(bookUpdateDto, existingBook)));
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

    /**
     * 指定されたIDの書籍情報を削除します。 削除に成功すると204 No Contentを返します。
     *
     * @param id 書籍ID
     * @return レスポンスエンティティ
     */
    @DeleteMapping("/v1/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteEmployees(@PathVariable String id) {
        bookCommandUseCase.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
