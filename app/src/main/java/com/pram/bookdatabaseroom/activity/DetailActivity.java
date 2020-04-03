package com.pram.bookdatabaseroom.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pram.bookdatabaseroom.R;
import com.pram.bookdatabaseroom.fragment.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initInstances();

        if (getIntent() != null) {
            bookId = getIntent().getIntExtra("bookId", 0);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, DetailFragment.newInstance(bookId))
                    .commit();
        }
    }

    private void initInstances() {

    }
}
