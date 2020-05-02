package com.pram.bookdatabaseroom.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pram.bookdatabaseroom.R;
import com.pram.bookdatabaseroom.activity.UpdateActivity;
import com.pram.bookdatabaseroom.db.BookDatabaseCallBack;
import com.pram.bookdatabaseroom.db.BookDatabaseController;
import com.pram.bookdatabaseroom.dialog.OneButtonDialogFragment;
import com.pram.bookdatabaseroom.dialog.TwoButtonDialogFragment;
import com.pram.bookdatabaseroom.manager.Contextor;
import com.pram.bookdatabaseroom.model.Book;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class DetailFragment extends Fragment implements
        OneButtonDialogFragment.OnDialogListener,
        TwoButtonDialogFragment.OnDialogListener {
    private static final String TAG = "DetailFragment";
    private Context mContext;
    private View mRootView;
    private BookDatabaseController bookDatabaseController;
    private Book mBook;
    private int mBookId;

    private TextView tvBookId;
    private TextView tvBookTitle;
    private TextView tvBookAuthor;
    private TextView tvBookPages;
    private Button btnUpdate;
    private Button btnRemove;

    public DetailFragment() {
        super();
    }

    @Override
    public void onOneButtonClick() {
        requireActivity().finish();
    }

    @Override
    public void onTwoButtonPositiveClick() {
        removeBook();
    }

    @Override
    public void onTwoButtonNegativeClick() {

    }

    @SuppressWarnings("unused")
    public static DetailFragment newInstance(int bookId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("bookId", bookId);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        mContext = Contextor.getInstance().getContext();
        bookDatabaseController = new BookDatabaseController();
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        mRootView = rootView;

        if (getArguments() != null) {
            mBookId = getArguments().getInt("bookId");
        }

        tvBookId = mRootView.findViewById(R.id.tvBookId);
        tvBookTitle = mRootView.findViewById(R.id.tvBookTitle);
        tvBookAuthor = mRootView.findViewById(R.id.tvBookAuthor);
        tvBookPages = mRootView.findViewById(R.id.tvBookPages);
        btnUpdate = mRootView.findViewById(R.id.btnUpdate);
        btnRemove = mRootView.findViewById(R.id.btnCancel);

        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, UpdateActivity.class);
            intent.putExtra("book", mBook);
            startActivity(intent);
        });

        btnRemove.setOnClickListener(v -> {
            callTwoButtonDialog("Remove?", "Ok", "Cancel");
        });

//        fetchDatabase();
    }

    private void fetchDatabase() {
        bookDatabaseController.getBook(mBookId, new BookDatabaseCallBack() {
            @Override
            public void onCallBack(Object result) {
                mBook = (Book) result;
                if (mBook != null) {
                    String id = mBook.getId().toString();
                    String title = mBook.getTitle();
                    String author = mBook.getAuthor();
                    String page = mBook.getPages();

                    tvBookId.setText(id);
                    tvBookTitle.setText(title);
                    tvBookAuthor.setText(author);
                    tvBookPages.setText(page);
                }
            }
        });
    }

    private void removeBook() {
        bookDatabaseController.removeBook(mBookId, new BookDatabaseCallBack() {
            @Override
            public void onCallBack(Object result) {
                callOneButtonDialog("Removed", "Ok");
            }
        });
    }

    private void callOneButtonDialog(String message, String submit) {
        OneButtonDialogFragment fragment = new OneButtonDialogFragment.Builder()
                .setMessage(message)
                .setSubmit(submit)
                .build();
        fragment.show(getChildFragmentManager(), "OneButtonDialogFragment");
    }

    private void callTwoButtonDialog(String message, String positive, String negative) {
        TwoButtonDialogFragment fragment = new TwoButtonDialogFragment.Builder()
                .setMessage(message)
                .setPositive(positive)
                .setNegative(negative)
                .build();
        fragment.show(getChildFragmentManager(), "TwoButtonDialogFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchDatabase();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }


    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }
}
