
package com.github.mobile.core.issue;

import android.accounts.Account;

import com.github.mobile.R;
import com.github.mobile.ui.DialogFragmentActivity;
import com.github.mobile.ui.ProgressDialogTask;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;

/**
 * Task to edit an entire issue
 */
public class EditIssueTask extends ProgressDialogTask<Issue> {

    @Inject
    private IssueStore store;

    private final IRepositoryIdProvider repositoryId;

    private final Issue issue;

    /**
     * Create task to edit a milestone
     *
     * @param activity
     * @param repositoryId
     * @param issue
     */
    public EditIssueTask(final DialogFragmentActivity activity,
            final IRepositoryIdProvider repositoryId, final Issue issue) {
        super(activity);

        this.repositoryId = repositoryId;
        this.issue = issue;
    }

    @Override
    protected Issue run(Account account) throws Exception {
        return store.editIssue(repositoryId, issue);
    }

    /**
     * Edit issue
     *
     * @return this task
     */
    public EditIssueTask edit() {
        showIndeterminate(R.string.updating_issue);

        execute();
        return this;
    }
}
