
package com.github.mobile.core.commit;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.github.mobile.util.HtmlUtils;
import com.github.mobile.util.HttpImageGetter;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.CommitComment;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.service.CommitService;

import java.util.List;

/**
 * Task to load a commit by SHA-1 id
 */
public class RefreshCommitTask extends AuthenticatedUserTask<FullCommit> {

    private static final String TAG = "RefreshCommitTask";

    @Inject
    private CommitStore store;

    @Inject
    private CommitService service;

    private final IRepositoryIdProvider repository;

    private final String id;

    private final HttpImageGetter imageGetter;

    /**
     * @param context
     * @param repository
     * @param id
     * @param imageGetter
     */
    public RefreshCommitTask(Context context, IRepositoryIdProvider repository,
            String id, HttpImageGetter imageGetter) {
        super(context);

        this.repository = repository;
        this.id = id;
        this.imageGetter = imageGetter;
    }

    @Override
    protected FullCommit run(Account account) throws Exception {
        RepositoryCommit commit = store.refreshCommit(repository, id);
        Commit rawCommit = commit.getCommit();
        if (rawCommit != null && rawCommit.getCommentCount() > 0) {
            List<CommitComment> comments = service.getComments(repository,
                    commit.getSha());
            for (CommitComment comment : comments) {
                String formatted = HtmlUtils.format(comment.getBodyHtml())
                        .toString();
                comment.setBodyHtml(formatted);
                imageGetter.encode(comment, formatted);
            }
            return new FullCommit(commit, comments);
        } else
            return new FullCommit(commit);
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception loading commit", e);
    }
}
