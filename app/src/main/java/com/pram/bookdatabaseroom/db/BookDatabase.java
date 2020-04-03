package com.pram.bookdatabaseroom.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pram.bookdatabaseroom.model.Book;

@Database(entities = {Book.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {

    private static BookDatabase instance;

    public abstract BookDao bookDao();

    public static synchronized  BookDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    BookDatabase.class, "book.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookDao bookDao;

        private PopulateDbAsyncTask(BookDatabase db) {
            bookDao = db.bookDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bookDao.createBook(new Book(1, ".NET Multithreading", "Alan Dennis", "360"));
            bookDao.createBook(new Book(2, "Unix Basic", "W. John Snow", "126"));
            bookDao.createBook(new Book(3, "Hello! Python", "Anthony Briggs", "352"));
            bookDao.createBook(new Book(4, "Software Requirements", "Benjamin L. Kovitz", "448"));
            return null;
        }
    }
}
