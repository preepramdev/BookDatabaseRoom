package com.pram.bookdatabaseroom.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pram.bookdatabaseroom.R;
import com.pram.bookdatabaseroom.db.BookDao;
import com.pram.bookdatabaseroom.db.BookDatabaseCallBack;
import com.pram.bookdatabaseroom.db.BookDatabaseController;
import com.pram.bookdatabaseroom.db.BookManager;
import com.pram.bookdatabaseroom.dialog.OneButtonDialogFragment;
import com.pram.bookdatabaseroom.dialog.TwoButtonDialogFragment;
import com.pram.bookdatabaseroom.manager.Contextor;
import com.pram.bookdatabaseroom.model.Book;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class AddFragment extends Fragment implements
        OneButtonDialogFragment.OnDialogListener,
        TwoButtonDialogFragment.OnDialogListener {
    private static final String TAG = "AddFragment";
    private Context mContext;
    private View mRootView;
    private Book mBook;
    private BookDao databaseManager;
    private BookDatabaseController bookDatabaseController;

    private EditText edtBookTitle;
    private EditText edtBookAuthor;
    private EditText edtBookPages;
    private Button btnAdd;
    private Button btnCancel;

    public AddFragment() {
        super();
    }

    @Override
    public void onOneButtonClick() {
        requireActivity().finish();
    }

    @Override
    public void onTwoButtonPositiveClick() {
        requireActivity().finish();
    }

    @Override
    public void onTwoButtonNegativeClick() {

    }

    @SuppressWarnings("unused")
    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
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

        edtBookTitle = mRootView.findViewById(R.id.edtBookTitle);
        edtBookAuthor = mRootView.findViewById(R.id.edtBookAuthor);
        edtBookPages = mRootView.findViewById(R.id.edtBookPages);
        btnAdd = mRootView.findViewById(R.id.btnAdd);
        btnCancel = mRootView.findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(v -> {
            addBook();
        });

        btnCancel.setOnClickListener(v -> {
            callTwoButtonDialog("Leave?", "Ok", "Cancel");
        });
    }

    private void addBook() {
        String title = edtBookTitle.getText().toString();
        String author = edtBookAuthor.getText().toString();
        String pages = edtBookPages.getText().toString();

        if (title.isEmpty() || author.isEmpty() || pages.isEmpty()) {
            Toast.makeText(mContext, "Check data", Toast.LENGTH_SHORT).show();
        } else {
            mBook = new Book(null, title, author, pages);

            bookDatabaseController.createBook(mBook, new BookDatabaseCallBack() {
                @Override
                public void onCallBack(Object result) {
                    callOneButtonDialog("Done", "Ok");
                }
            });
        }
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }
}
