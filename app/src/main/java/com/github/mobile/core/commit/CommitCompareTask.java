
package com.github.mobile.core.commit;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommitCompare;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

/**
 * Task to compare two commits
 */
public class CommitCompareTask extends
        AuthenticatedUserTask<RepositoryCommitCompare> {

    private static final String TAG = "CommitCompareTask";

    @Inject
    private CommitService commitService;

    @Inject
    private RepositoryService repositoryService;

    private final IRepositoryIdProvider repository;

    private String base;

    private String head;

    /**
     * @param context
     * @param repository
     * @param base
     * @param head
     */
    public CommitCompareTask(Context context, IRepositoryIdProvider repository,
            String base, String head) {
        super(context);

        this.repository = repository;
        this.base = base;
        this.head = head;
    }

    @Override
    protected RepositoryCommitCompare run(Account account) throws Exception {
        if (TextUtils.isEmpty(base)) {
            base = repositoryService.getRepository(repository).getDefaultBranch();
        }
        return commitService.compare(repository, base, head);
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception loading commit compare", e);
    }
}
