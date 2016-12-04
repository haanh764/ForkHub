
package com.github.mobile.core.user;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.service.UserService;

/**
 * Task to check user following status
 */
public class FollowingUserTask extends AuthenticatedUserTask<Boolean> {

    private static final String TAG = "FollowingUserTask";

    @Inject
    private UserService service;

    private final String login;

    /**
     * Create task for context and login
     *
     * @param context
     * @param login
     */
    public FollowingUserTask(final Context context, final String login) {
        super(context);

        this.login = login;
    }

    @Override
    protected Boolean run(final Account account) throws Exception {
        return service.isFollowing(login);
    }

    @Override
    protected void onException(final Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception checking if following user", e);
    }
}
