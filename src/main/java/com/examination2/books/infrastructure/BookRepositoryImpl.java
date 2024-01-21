package com.examination2.books.infrastructure;

import static java.util.Collections.emptyList;

import com.examination2.books.domain.Book;
import com.examination2.books.domain.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
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
}
