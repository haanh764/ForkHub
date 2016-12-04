
package com.github.mobile.core.gist;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.github.mobile.util.HtmlUtils;
import com.github.mobile.util.HttpImageGetter;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.service.GistService;

import java.util.Collections;
import java.util.List;

/**
 * Task to load and store a {@link Gist}
 */
public class RefreshGistTask extends AuthenticatedUserTask<FullGist> {

    private static final String TAG = "RefreshGistTask";

    @Inject
    private GistStore store;

    @Inject
    private GistService service;

    private final String id;

    private final HttpImageGetter imageGetter;

    /**
     * Create task to refresh the given {@link Gist}
     *
     * @param context
     * @param gistId
     * @param imageGetter
     */
    public RefreshGistTask(Context context, String gistId,
            HttpImageGetter imageGetter) {
        super(context);

        id = gistId;
        this.imageGetter = imageGetter;
    }

    @Override
    public FullGist run(Account account) throws Exception {
        Gist gist = store.refreshGist(id);
        List<Comment> comments;
        if (gist.getComments() > 0)
            comments = service.getComments(id);
        else
            comments = Collections.emptyList();
        for (Comment comment : comments) {
            String formatted = HtmlUtils.format(comment.getBodyHtml())
                    .toString();
            comment.setBodyHtml(formatted);
            imageGetter.encode(comment, formatted);
        }
        return new FullGist(gist, service.isStarred(id), comments);
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception loading gist", e);
    }
}