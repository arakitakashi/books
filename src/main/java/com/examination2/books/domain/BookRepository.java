package com.examination2.books.domain;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();

    Optional<Book> findById(String id);

    Book register(Book book);

    Book update(Book book);

    boolean delete(String id);
}