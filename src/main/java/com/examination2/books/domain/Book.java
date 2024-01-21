package com.examination2.books.domain;

import static com.examination2.books.domain.DomainFieldValidator.validateStringField;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.util.Objects;

public class Book {
    private final String id;
    private final String title;
    private final String author;
    private final String publisher;
    private final String price;

    private Book(String id, String title, String author, String publisher, String price) {
        validateStringField("Title", title);
        validateStringField("Author", author);
        validateStringField("Publisher", publisher);
        if(!isNumeric(price) || Integer.parseInt(price) <= 0 ) throw new IllegalArgumentException("Price should be positive numeric value");

        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }

    public static Book create(String id, String title, String author, String publisher, String price) {
        return new Book(id, title, author, publisher, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book book)) {
            return false;
        }
        return Objects.equals(id, book.id) && Objects.equals(title, book.title)
            && Objects.equals(author, book.author) && Objects.equals(publisher,
            book.publisher) && Objects.equals(price, book.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publisher, price);
    }
}
