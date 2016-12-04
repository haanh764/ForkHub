
package com.github.mobile.core.gist;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.service.GistService;

/**
 * Task to unstar a {@link Gist}
 */
public class UnstarGistTask extends AuthenticatedUserTask<Gist> {

    private static final String TAG = "UnstarGistTask";

    @Inject
    private GistService service;

    private final String id;

    /**
     * Create task to unstar a {@link Gist}
     *
     * @param context
     * @param id
     */
    public UnstarGistTask(final Context context, final String id) {
        super(context);

        this.id = id;
    }

    @Override
    public Gist run(Account account) throws Exception {
        service.unstarGist(id);
        return null;
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception unstarring gist", e);
    }
}
