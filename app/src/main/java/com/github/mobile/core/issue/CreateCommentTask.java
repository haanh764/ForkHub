
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
 * Task to comment on an issue in a repository
 */
public class CreateCommentTask extends ProgressDialogTask<Comment> {

    private static final String TAG = "CreateCommentTask";

    private final IRepositoryIdProvider repository;

    private final int issueNumber;

    private final String comment;

    @Inject
    private IssueService service;

    /**
     * Create task for creating a comment on the given issue in the given
     * repository
     *
     * @param context
     * @param repository
     * @param issueNumber
     * @param comment
     */
    public CreateCommentTask(final Context context,
            final IRepositoryIdProvider repository, final int issueNumber,
            final String comment) {
        super(context);

        this.repository = repository;
        this.issueNumber = issueNumber;
        this.comment = comment;
    }

    @Override
    protected Comment run(Account account) throws Exception {
        Comment created = service.createComment(repository, issueNumber,
                comment);
        String formatted = HtmlUtils.format(created.getBodyHtml()).toString();
        created.setBodyHtml(formatted);
        return created;
    }

    /**
     * Create comment
     *
     * @return this task
     */
    public CreateCommentTask start() {
        showIndeterminate(R.string.creating_comment);

        execute();
        return this;
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception creating comment on issue", e);

        ToastUtils.show((Activity) getContext(), e.getMessage());
    }
}
