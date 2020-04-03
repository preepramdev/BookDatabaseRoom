package com.pram.bookdatabaseroom.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pram.bookdatabaseroom.R;
import com.pram.bookdatabaseroom.activity.AddActivity;
import com.pram.bookdatabaseroom.activity.DetailActivity;
import com.pram.bookdatabaseroom.adapter.BookAdapter;
import com.pram.bookdatabaseroom.db.BookDao;
import com.pram.bookdatabaseroom.db.BookDatabaseCallBack;
import com.pram.bookdatabaseroom.db.BookDatabaseController;
import com.pram.bookdatabaseroom.db.BookManager;
import com.pram.bookdatabaseroom.manager.Contextor;
import com.pram.bookdatabaseroom.model.Book;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private Context mContext;
    private View mRootView;
    private BookDao databaseManager;
    private BookDatabaseController bookDatabaseController;

    private List<Book> mModels;
    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView rvBookList;
    private FloatingActionButton fabAdd;

    public MainFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        mContext = Contextor.getInstance().getContext();
        databaseManager = BookManager.getInstance().getBookDatabase().bookDao();
        bookDatabaseController = new BookDatabaseController();
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        mRootView = rootView;
        mModels = new ArrayList();
        mAdapter = new BookAdapter(mModels);
        mLayoutManager = new LinearLayoutManager(mContext);

        rvBookList = mRootView.findViewById(R.id.rvBookList);
        fabAdd = mRootView.findViewById(R.id.fabAdd);

        rvBookList.setLayoutManager(mLayoutManager);
        rvBookList.setHasFixedSize(true);
        rvBookList.setAdapter(mAdapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, AddActivity.class);
            startActivity(intent);
        });

        mAdapter.setOnItemClickListener(position -> {
            Log.e(TAG, "onItemClick: " + position);
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("bookId", mModels.get(position).getId());
            startActivity(intent);
        });

//        fetchDatabase();
    }

    private void fetchDatabase() {
        bookDatabaseController.getAllBooks(new BookDatabaseCallBack() {
            @Override
            public void onCallBack(Object result) {
                List<Book> returnBooks = (List<Book>) result;
                mModels.addAll(returnBooks);
            }
        });
        mAdapter.notifyDataSetChanged(); /*Adapter must notify outside*/
    }

    @Override
    public void onResume() {
        super.onResume();
        mModels.clear();
        fetchDatabase();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
    }

}
