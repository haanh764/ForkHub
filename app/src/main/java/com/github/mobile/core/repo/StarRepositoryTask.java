
package com.github.mobile.core.repo;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.R;
import com.github.mobile.ui.ProgressDialogTask;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.service.StargazerService;

/**
 * Task to star a repository
 */
public class StarRepositoryTask extends ProgressDialogTask<Void> {

    private static final String TAG = "StarRepositoryTask";

    @Inject
    private StargazerService service;

    private final IRepositoryIdProvider repo;

    /**
     * Create task for context and id provider
     *
     * @param context
     * @param repo
     */
    public StarRepositoryTask(Context context, IRepositoryIdProvider repo) {
        super(context);

        this.repo = repo;
    }

    /**
     * Execute the task with a progress dialog displaying.
     * <p>
     * This method must be called from the main thread.
     */
    public void start() {
        showIndeterminate(R.string.starring_repository);

        execute();
    }

    @Override
    protected Void run(Account account) throws Exception {
        service.star(repo);

        return null;
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception starring repository", e);
    }
}
