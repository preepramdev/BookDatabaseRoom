package com.pram.bookdatabaseroom;

import android.app.Application;

import com.pram.bookdatabaseroom.db.BookDatabase;
import com.pram.bookdatabaseroom.db.BookManager;
import com.pram.bookdatabaseroom.manager.Contextor;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize thing(s) here
        Contextor.getInstance().init(getApplicationContext());
        BookManager.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
