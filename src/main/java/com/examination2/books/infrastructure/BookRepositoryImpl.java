package com.examination2.books.infrastructure;

import static java.util.Collections.emptyList;

import com.examination2.books.domain.book.Book;
import com.examination2.books.domain.book.BookRepository;
import com.examination2.books.domain.exception.BookNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link BookRepository}のJDBCによる実装。 書籍情報のデータベース操作を担います。
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private static final String DATABASE_ACCESS_ERROR_MESSAGE = "Database Access Error";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> findAll() {
        String query = "SELECT id, title, author, publisher, price FROM books";

        try {
            return jdbcTemplate.query(query, new DataClassRowMapper<>(Book.class));
        } catch (DataAccessException e) {
            log.warn("Data access error.", e);
            return emptyList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Book> findById(String id) {
        String query = "SELECT id, title, author, publisher, price FROM books WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", Integer.parseInt(id));

        try {
            Book book = jdbcTemplate.queryForObject(query, params,
                new DataClassRowMapper<>(Book.class));
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            throw new BookNotFoundException(createNotFoundMessage(id));
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book register(Book book) {
        String sequenceQuery = "SELECT nextval('BOOK_ID_SEQ')";
        Integer id = jdbcTemplate.queryForObject(sequenceQuery, new HashMap<>(), Integer.class);

        String query = """
            INSERT INTO books (id, title, author, publisher, price) 
            VALUES (:id, :title, :author, :publisher, :price)
            """;

        Map<String, Object> params = createSaveParams(id, book);

        try {
            jdbcTemplate.update(query, params);
            return Book.create(String.valueOf(id), book.getTitle(), book.getAuthor(),
                book.getPublisher(), String.valueOf(book.getPrice()));
        } catch (DataAccessException e) {
            log.error(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    private Map<String, Object> createSaveParams(int id, Book book) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", book.getTitle());
        result.put("author", book.getAuthor());
        result.put("publisher", book.getPublisher());
        result.put("price", book.getPrice());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Book update(Book book) {
        String query = """
            UPDATE books 
            SET title = :title, author = :author, publisher = :publisher, price = :price 
            WHERE id = :id
            """;

        Map<String, Object> params = createUpdateParams(book);

        try {
            int updated = jdbcTemplate.update(query, params);
            if (updated > 0) {
                return book;
            }
            log.warn("Book was not found");
            throw new BookNotFoundException(createNotFoundMessage(book.getId()));
        } catch (DataAccessException e) {
            log.error(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    private Map<String, Object> createUpdateParams(Book book) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", Integer.parseInt(book.getId()));
        result.put("title", book.getTitle());
        result.put("author", book.getAuthor());
        result.put("publisher", book.getPublisher());
        result.put("price", book.getPrice());
        return result;
    }

    private String createNotFoundMessage(String id) {
        return "specified book [id = " + id + "] is not found.";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean delete(String id) {
        try {
            String query = "DELETE FROM books WHERE id = :id";

            Map<String, Object> params = createDeleteParams(id);

            int affectedRows = jdbcTemplate.update(query, params);

            if (affectedRows == 0) {
                throw new BookNotFoundException((createNotFoundMessage(id)));
            }
            return affectedRows > 0;
        } catch (DataAccessException e) {
            log.error(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }

    private Map<String, Object> createDeleteParams(String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", Integer.parseInt(id));
        return result;
    }
}
