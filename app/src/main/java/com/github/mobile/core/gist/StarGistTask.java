
package com.github.mobile.core.gist;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.service.GistService;

/**
 * Task to star a {@link Gist}
 */
public class StarGistTask extends AuthenticatedUserTask<Gist> {

    private static final String TAG = "StarGistTask";

    @Inject
    private GistService service;

    private final String id;

    /**
     * Create task to star a {@link Gist}
     *
     * @param context
     * @param id
     */
    public StarGistTask(final Context context, final String id) {
        super(context);

        this.id = id;
    }

    @Override
    public Gist run(Account account) throws Exception {
        service.starGist(id);
        return null;
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception starring gist", e);
    }
}
