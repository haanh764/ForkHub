
package com.github.mobile.core.issue;

import android.accounts.Account;
import android.app.Activity;
import android.util.Log;

import com.github.mobile.R;
import com.github.mobile.ui.ProgressDialogTask;
import com.github.mobile.util.ToastUtils;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.service.IssueService;

/**
 * Task to create an {@link Issue}
 */
public class CreateIssueTask extends ProgressDialogTask<Issue> {

    private static final String TAG = "CreateIssueTask";

    @Inject
    private IssueService service;

    @Inject
    private IssueStore store;

    private final IRepositoryIdProvider repository;

    private final Issue issue;

    /**
     * Create task to create an {@link Issue}
     *
     * @param activity
     * @param repository
     * @param issue
     */
    public CreateIssueTask(final Activity activity,
            final IRepositoryIdProvider repository, final Issue issue) {
        super(activity);

        this.repository = repository;
        this.issue = issue;
    }

    /**
     * Create issue
     *
     * @return this task
     */
    public CreateIssueTask create() {
        showIndeterminate(R.string.creating_issue);

        execute();
        return this;
    }

    @Override
    public Issue run(Account account) throws Exception {
        return store.addIssue(service.createIssue(repository, issue));
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.e(TAG, "Exception creating issue", e);
        ToastUtils.show((Activity) getContext(), e.getMessage());
    }
}
