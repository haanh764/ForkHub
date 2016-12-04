
package com.github.mobile.core.issue;

import android.accounts.Account;

import com.github.mobile.R;
import com.github.mobile.ui.DialogFragmentActivity;
import com.github.mobile.ui.ProgressDialogTask;
import com.github.mobile.ui.issue.MilestoneDialog;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.service.MilestoneService;

import static com.github.mobile.RequestCodes.ISSUE_MILESTONE_UPDATE;

/**
 * Task to edit a milestone
 */
public class EditMilestoneTask extends ProgressDialogTask<Issue> {

    @Inject
    private MilestoneService service;

    @Inject
    private IssueStore store;

    private final MilestoneDialog milestoneDialog;

    private final IRepositoryIdProvider repositoryId;

    private final int issueNumber;

    private int milestoneNumber;

    /**
     * Create task to edit a milestone
     *
     * @param activity
     * @param repositoryId
     * @param issueNumber
     */
    public EditMilestoneTask(final DialogFragmentActivity activity,
            final IRepositoryIdProvider repositoryId, final int issueNumber) {
        super(activity);

        this.repositoryId = repositoryId;
        this.issueNumber = issueNumber;
        milestoneDialog = new MilestoneDialog(activity, ISSUE_MILESTONE_UPDATE,
                repositoryId, service);
    }

    @Override
    protected Issue run(Account account) throws Exception {
        Issue editedIssue = new Issue();
        editedIssue.setNumber(issueNumber);
        editedIssue.setMilestone(new Milestone().setNumber(milestoneNumber));
        return store.editIssue(repositoryId, editedIssue);
    }

    /**
     * Prompt for milestone selection
     *
     * @param milestone
     *            current milestone
     * @return this task
     */
    public EditMilestoneTask prompt(Milestone milestone) {
        milestoneDialog.show(milestone);
        return this;
    }

    /**
     * Edit issue to have given milestone
     *
     * @param milestone
     * @return this task
     */
    public EditMilestoneTask edit(Milestone milestone) {
        if (milestone != null)
            milestoneNumber = milestone.getNumber();
        else
            milestoneNumber = -1;

        showIndeterminate(R.string.updating_milestone);

        super.execute();

        return this;
    }
}
