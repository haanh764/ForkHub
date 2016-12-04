
package com.github.mobile.core.issue;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.github.mobile.R;
import com.github.mobile.ui.ProgressDialogTask;
import com.github.mobile.util.ToastUtils;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.service.IssueService;

/**
 * Task to delete a comment on an issue in a repository
 */
public class DeleteCommentTask extends ProgressDialogTask<Comment> {

    private static final String TAG = "DeleteCommentTask";

    private final IRepositoryIdProvider repository;

    private final Comment comment;

    @Inject
    private IssueService service;

    /**
     * Delete task for deleting a comment on the given issue in the given
     * repository
     *
     * @param context
     * @param repository
     * @param comment
     */
    public DeleteCommentTask(final Context context,
            final IRepositoryIdProvider repository,
            final Comment comment) {
        super(context);

        this.repository = repository;
        this.comment = comment;
    }

    @Override
    protected Comment run(Account account) throws Exception {
        service.deleteComment(repository, comment.getId());
        return comment;
    }

    /**
     * Delete comment
     *
     * @return this task
     */
    public DeleteCommentTask start() {
        showIndeterminate(R.string.deleting_comment);

        execute();
        return this;
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception deleting comment on issue", e);

        ToastUtils.show((Activity) getContext(), e.getMessage());
    }
}
