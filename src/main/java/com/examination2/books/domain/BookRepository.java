package com.examination2.books.domain;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();
}
