package com.examination2.books.domain;

import java.util.List;
import java.util.Optional;

/**
 * 書籍情報エンティティに対するリポジトリのインターフェース。 書籍情報の検索、登録、更新、削除の操作を定義します。
 */
public interface BookRepository {
    /**
     * 全ての書籍情報を取得します。
     *
     * @return 書籍情報のリスト
     */
    List<Book> findAll();

    /**
     * 指定されたIDを持つ書籍情報を取得します。 書籍情報が存在しない場合は空のOptionalを返します。
     *
     * @param id 書籍情報ID
     * @return 指定されたIDの書籍情報を含むOptionalオブジェクト
     */
    Optional<Book> findById(String id);

    /**
     * 新しい書籍情報を登録します。 登録された書籍情報を返します。
     *
     * @param book 登録する書籍の情報
     * @return 登録された書籍
     */
    Book register(Book book);

    /**
     * 既存の書籍情報を更新します。 更新された書籍情報を返します。
     *
     * @param book 更新する書籍の情報
     * @return 更新された書籍
     */
    Book update(Book book);

    /**
     * 指定されたIDの書籍情報を削除します。 削除が成功した場合はtrue、失敗した場合はfalseを返します。
     *
     * @param id 削除する書籍のID
     * @return 削除の成否
     */
    boolean delete(String id);
}