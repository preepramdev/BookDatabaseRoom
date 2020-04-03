package com.pram.bookdatabaseroom.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

/**
 * Created by nuuneoi on 12/6/14 AD.
 */
public class BookManager {

    private static BookManager instance;

    public static BookManager getInstance() {
        if (instance == null)
            instance = new BookManager();
        return instance;
    }

    private BookDatabase bookDatabase;

    private BookManager() {

    }

    public void init(Context context) {
        bookDatabase = BookDatabase.getInstance(context);
    }

    public BookDatabase getBookDatabase() {
        return bookDatabase;
    }
}
