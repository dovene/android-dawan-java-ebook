package com.dov.ebookjava.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(BookEntity book);

    @Query("SELECT * FROM book")
    List<BookEntity> getAll();

    @Query("DELETE FROM book WHERE id = :id")
    void deleteByIdString (String id);

    @Query("SELECT * FROM book WHERE id = :id")
    BookEntity getBookById(String id);
}
