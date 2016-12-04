
package com.github.mobile.core.commit;

import android.accounts.Account;
import android.app.Activity;
import android.util.Log;

import com.github.mobile.R;
import com.github.mobile.ui.ProgressDialogTask;
import com.github.mobile.util.HtmlUtils;
import com.github.mobile.util.ToastUtils;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.CommitComment;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.service.CommitService;

/**
 * Task to comment on a commit
 */
public class CreateCommentTask extends ProgressDialogTask<CommitComment> {

    private static final String TAG = "CreateCommentTask";

    @Inject
    private CommitService service;

    private final IRepositoryIdProvider repository;

    private final String commit;

    private final CommitComment comment;

    /**
     * Create task to create a comment
     *
     * @param activity
     * @param repository
     * @param commit
     * @param comment
     */
    protected CreateCommentTask(final Activity activity,
            final IRepositoryIdProvider repository, final String commit,
            final CommitComment comment) {
        super(activity);

        this.repository = repository;
        this.commit = commit;
        this.comment = comment;
    }

    /**
     * Execute the task and create the comment
     *
     * @return this task
     */
    public CreateCommentTask start() {
        showIndeterminate(R.string.creating_comment);
        execute();
        return this;
    }

    @Override
    public CommitComment run(final Account account) throws Exception {
        CommitComment created = service.addComment(repository, commit, comment);
        String formatted = HtmlUtils.format(created.getBodyHtml()).toString();
        created.setBodyHtml(formatted);
        return created;

    }

    @Override
    protected void onException(final Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception creating comment on commit", e);

        ToastUtils.show((Activity) getContext(), e.getMessage());
    }
}
