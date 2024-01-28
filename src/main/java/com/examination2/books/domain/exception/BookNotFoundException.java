package com.examination2.books.domain.exception;

/**
 * 書籍情報が見つからない場合にスローされる例外。 この例外は、書籍情報の検索操作で対象の書籍情報がデータベースに存在しない場合に使用されます。
 */
public class BookNotFoundException extends RuntimeException {
    /**
     * 指定されたメッセージを持つ{@link BookNotFoundException}を構築します。
     *
     * @param message 例外に関連する詳細メッセージ
     */
    public BookNotFoundException(String message) {
        super(message);
    }
}
