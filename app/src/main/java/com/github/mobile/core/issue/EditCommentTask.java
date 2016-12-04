
package com.github.mobile.core.issue;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.github.mobile.R;
import com.github.mobile.ui.ProgressDialogTask;
import com.github.mobile.util.HtmlUtils;
import com.github.mobile.util.ToastUtils;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.service.IssueService;

/**
 * Task to edit a comment on an issue in a repository
 */
public class EditCommentTask extends ProgressDialogTask<Comment> {

    private static final String TAG = "EditCommentTask";

    private final IRepositoryIdProvider repository;

    private final Comment comment;

    @Inject
    private IssueService service;

    /**
     * Edit task for editing a comment on the given issue in the given
     * repository
     *
     * @param context
     * @param repository
     * @param comment
     */
    public EditCommentTask(final Context context,
            final IRepositoryIdProvider repository,
            final Comment comment) {
        super(context);

        this.repository = repository;
        this.comment = comment;
    }

    @Override
    protected Comment run(Account account) throws Exception {
        Comment edited = service.editComment(repository, comment);
        String formatted = HtmlUtils.format(edited.getBodyHtml()).toString();
        edited.setBodyHtml(formatted);
        return edited;
    }

    /**
     * Edit comment
     *
     * @return this task
     */
    public EditCommentTask start() {
        showIndeterminate(R.string.editing_comment);

        execute();
        return this;
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception editing comment on issue", e);

        ToastUtils.show((Activity) getContext(), e.getMessage());
    }
}
