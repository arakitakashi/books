package com.examination2.books.domain.book;

import static com.examination2.books.domain.DomainFieldValidator.validateStringField;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.util.Objects;

/**
 * 書籍エンティティを表すクラス。 書籍の基本情報であるタイトル、著者、出版社、価格、IDを保持します。
 */
public class Book {

    private final String id;
    private final String title;
    private final String author;
    private final String publisher;
    private final int price;

    private Book(String id, String title, String author, String publisher, String price) {
        validateStringField("Title", title);
        validateStringField("Author", author);
        validateStringField("Publisher", publisher);
        if (!isNumeric(price) || Integer.parseInt(price) <= 0) {
            throw new IllegalArgumentException("Price should be positive numeric value");
        }

        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = Integer.parseInt(price);
    }

    /**
     * 書籍インスタンスをIDを含む形で生成します。
     *
     * @param id        書籍ID
     * @param title     タイトル
     * @param author    著者
     * @param publisher 出版社
     * @param price     価格
     * @return 新しい書籍インスタンス
     */
    public static Book create(String id, String title, String author, String publisher,
        String price) {
        return new Book(id, title, author, publisher, price);
    }

    /**
     * 書籍インスタンスをIDを含まない形で生成します。
     *
     * @param title     タイトル
     * @param author    著者
     * @param publisher 出版社
     * @param price     価格
     * @return 新しい書籍インスタンス
     */
    public static Book createWithoutId(String title, String author, String publisher,
        String price) {
        return new Book(null, title, author, publisher, price);
    }

    /**
     * 書籍のIDを取得します。
     *
     * @return 書籍ID
     */
    public String getId() {
        return id;
    }

    /**
     * 書籍のタイトルを取得します。
     *
     * @return タイトル
     */
    public String getTitle() {
        return title;
    }

    /**
     * 書籍の著者を取得します。
     *
     * @return 著者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 書籍の出版社を取得します。
     *
     * @return 出版社
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * 書籍の価格を取得します。
     *
     * @return 価格
     */
    public int getPrice() {
        return price;
    }

    /**
     * オブジェクトの等価性を確認してbooleanで返す。 全てのフィールドの等価性を確認する。
     *
     * @param o 比較対象のオブジェクト
     * @return 比較の結果
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book book)) {
            return false;
        }
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(
            author, book.author) && Objects.equals(publisher, book.publisher) && Objects.equals(
            price, book.price);
    }

    /**
     * オブジェクトのハッシュコードを生成する。
     *
     * @return ハッシュコード
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publisher, price);
    }

    /**
     * オブジェクトの文字列表示を生成する。
     *
     * @return オブジェクトの文字列表示
     */
    @Override
    public String toString() {
        return "Book{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", author='" + author
            + '\'' + ", publisher='" + publisher + '\'' + ", price='" + price + '\'' + '}';
    }
}
