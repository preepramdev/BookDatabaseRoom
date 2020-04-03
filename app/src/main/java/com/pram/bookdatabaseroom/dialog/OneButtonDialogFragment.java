package com.pram.bookdatabaseroom.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.pram.bookdatabaseroom.R;


public class OneButtonDialogFragment extends DialogFragment {
    private static final String KEY_MESSAGE = "key_message";
    private static final String KEY_SUBMIT = "key_submit";

    private TextView tvMessage;
    private Button btnSubmit;

    private String message;
    private String submit;

    public interface OnDialogListener {
        void onOneButtonClick();
    }

    public static OneButtonDialogFragment newInstance(String message, String submit) {
        OneButtonDialogFragment fragment = new OneButtonDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MESSAGE, message);
        bundle.putString(KEY_SUBMIT, submit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            restoreArguments(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_one_button, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setupView();
    }

    private void bindView(View view) {
        tvMessage = view.findViewById(R.id.tvMessage);
        btnSubmit = view.findViewById(R.id.btnSubmit);
    }

    private void setupView() {
        tvMessage.setText(message);
        btnSubmit.setText(submit);

        btnSubmit.setOnClickListener(v -> {
            OnDialogListener listener = getOnDialogListener();
            if (listener != null) {
                listener.onOneButtonClick();
            }
            dismiss();
        });
    }

    private OnDialogListener getOnDialogListener() {
        Fragment fragment = getParentFragment();
        try {
            if (fragment != null) {
                return (OnDialogListener) fragment;
            } else {
                return (OnDialogListener) getActivity();
            }
        } catch (ClassCastException ignored) {
        }
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MESSAGE, message);
        outState.putString(KEY_SUBMIT, submit);
    }

    private void restoreInstanceState(Bundle bundle) {
        message = bundle.getString(KEY_MESSAGE);
        submit = bundle.getString(KEY_SUBMIT);
    }

    private void restoreArguments(Bundle bundle) {
        message = bundle.getString(KEY_MESSAGE);
        submit = bundle.getString(KEY_SUBMIT);
    }

    public static class Builder {
        private String message;
        private String submit;

        public Builder() {
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setSubmit(String submit) {
            this.submit = submit;
            return this;
        }

        public OneButtonDialogFragment build() {
            return OneButtonDialogFragment.newInstance(message, submit);
        }
    }
}
