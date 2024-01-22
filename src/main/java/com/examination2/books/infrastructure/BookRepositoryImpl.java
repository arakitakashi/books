package com.examination2.books.infrastructure;

import static java.util.Collections.emptyList;

import com.examination2.books.domain.Book;
import com.examination2.books.domain.BookRepository;
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
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private static final String DATABASE_ACCESS_ERROR_MESSAGE = "Database Access Error";
    private final NamedParameterJdbcTemplate jdbcTemplate;

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

    @Override
    public Optional<Book> findById(String id) {
        String query = "SELECT id, title, author, publisher, price FROM books WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        try {
            Book book = jdbcTemplate.queryForObject(query, params, new DataClassRowMapper<>(Book.class));
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            throw new BookNotFoundException("specified book [id = " + id + "] is not found.");
        } catch (DataAccessException e) {
            log.warn(DATABASE_ACCESS_ERROR_MESSAGE, e);
            throw e;
        }
    }
}
