package com.pram.bookdatabaseroom.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pram.bookdatabaseroom.model.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM book_list")
    List<Book> getAllBooks();

    @Query("SELECT * FROM book_list WHERE id = :bookId")
    Book getBook(int bookId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createBook(Book book); // When Insert is Success, this will return Primary Key

    @Update
    void updateBook(Book book);

    /*@Delete
    void delete(Book book);*/

    @Query("DELETE FROM book_list WHERE id = :bookId")
    void removeBook(int bookId);
}
