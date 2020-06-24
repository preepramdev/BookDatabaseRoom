package com.pram.bookdatabaseroom.db;

import android.content.Context;
import android.util.Log;

import com.pram.bookdatabaseroom.manager.Contextor;
import com.pram.bookdatabaseroom.model.Book;

import java.util.List;

public class BookDatabaseController {
    private static final String TAG = "BookDatabaseController";
    private Context mContext;
    private BookDao databaseManager;

    private Object emptyResult = new Object();

    public BookDatabaseController() {
        mContext = Contextor.getInstance().getContext();
        databaseManager = BookManager.getInstance().getBookDatabase().bookDao();
    }

    public void getAllBooks(BookDatabaseCallBack callBack) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                List<Book> books = databaseManager.getAllBooks();

                for (Book book : books) {
                    Log.e(TAG, "-->");
                    Log.e(TAG, "fetchDatabase bookId: " + book.getId());
                    Log.e(TAG, "fetchDatabase bookTitle: " + book.getTitle());
                    Log.e(TAG, "fetchDatabase bookAuthor: " + book.getAuthor());
                    Log.e(TAG, "fetchDatabase bookPages: " + book.getPages());
                    Log.e(TAG, "<--");
                }

                callBack.onCallBack(books);
            }
        };
        thread.start();
    }

    public void getBook(int bookId, BookDatabaseCallBack callBack) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                Book book = databaseManager.getBook(bookId);
                callBack.onCallBack(book);
            }
        };
        thread.start();
    }

    public void createBook(Book book, BookDatabaseCallBack callBack) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                databaseManager.createBook(book);
                callBack.onCallBack(emptyResult); // void
            }
        };
        thread.start();
    }

    public void updateBook(Book book, BookDatabaseCallBack callBack) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                databaseManager.updateBook(book);
                callBack.onCallBack(emptyResult); // void
            }
        };
        thread.start();
    }

    public void removeBook(int bookId, BookDatabaseCallBack callBack) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                databaseManager.removeBook(bookId);
                callBack.onCallBack(emptyResult); // void
            }
        };
        thread.start();
    }
}
