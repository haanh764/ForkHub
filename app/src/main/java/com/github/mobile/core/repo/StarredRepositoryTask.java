
package com.github.mobile.core.repo;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.service.StargazerService;

/**
 * Task to check repository starring status
 */
public class StarredRepositoryTask extends AuthenticatedUserTask<Boolean> {

    private static final String TAG = "StarringRepositoryTask";

    @Inject
    private StargazerService service;

    private final IRepositoryIdProvider repo;

    /**
     * Create task for context and id provider
     *
     * @param context
     * @param repo
     */
    public StarredRepositoryTask(Context context, IRepositoryIdProvider repo) {
        super(context);

        this.repo = repo;
    }

    @Override
    protected Boolean run(Account account) throws Exception {
        return service.isStarring(repo);
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception checking starring repository status", e);
    }
}
