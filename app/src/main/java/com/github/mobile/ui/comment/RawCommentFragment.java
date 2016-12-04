
package com.github.mobile.ui.comment;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.mobile.R;
import com.github.mobile.ui.DialogFragment;
import com.github.mobile.ui.TextWatcherAdapter;

/**
 * Fragment to display raw comment text
 */
public class RawCommentFragment extends DialogFragment {

    private EditText commentText;

    /**
     * Text to populate comment window.
     */
    private String initComment;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        commentText = finder.find(R.id.et_comment);
        commentText.addTextChangedListener(new TextWatcherAdapter() {

            @Override
            public void afterTextChanged(Editable s) {
                Activity activity = getActivity();
                if (activity != null)
                    activity.invalidateOptionsMenu();
            }
        });
        commentText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                commentText.requestFocusFromTouch();
                return false;
            }
        });

        setText(initComment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.comment_create, null);
    }

    /**
     * Get comment text
     *
     * @return text
     */
    public String getText() {
        return commentText.getText().toString();
    }

    /**
     * Set comment text
     */
    public void setText(String comment) {
        if (commentText != null) {
            commentText.setText(comment);
            commentText.selectAll();
        } else {
            initComment = comment;
        }
    }
}
