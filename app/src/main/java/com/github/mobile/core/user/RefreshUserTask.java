
package com.github.mobile.core.user;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.UserService;

/**
 * Task to refresh a user
 */
public class RefreshUserTask extends AuthenticatedUserTask<User> {

    private static final String TAG = "RefreshUserTask";

    @Inject
    private UserService service;

    private final String login;

    /**
     * Create task for context and login
     *
     * @param context
     * @param login
     */
    public RefreshUserTask(Context context, String login) {
        super(context);

        this.login = login;
    }

    @Override
    protected User run(Account account) throws Exception {
        return service.getUser(login);
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception loading user", e);
    }
}