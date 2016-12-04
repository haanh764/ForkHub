
package com.github.mobile.core.user;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.R;
import com.github.mobile.ui.ProgressDialogTask;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.UserService;

/**
 * Task to follow a user
 */
public class FollowUserTask extends ProgressDialogTask<User> {

    private static final String TAG = "FollowUserTask";

    @Inject
    private UserService service;

    private final String login;

    /**
     * Create task for context and login
     *
     * @param context
     * @param login
     */
    public FollowUserTask(final Context context, final String login) {
        super(context);

        this.login = login;
    }

    /**
     * Execute the task with a progress dialog displaying.
     * <p>
     * This method must be called from the main thread.
     */
    public void start() {
        showIndeterminate(R.string.following_user);

        execute();
    }

    @Override
    protected User run(final Account account) throws Exception {
        service.follow(login);

        return null;
    }

    @Override
    protected void onException(final Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception following user", e);
    }
}
