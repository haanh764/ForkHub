
package com.github.mobile.core.gist;

import android.accounts.Account;
import android.app.Activity;
import android.util.Log;

import com.github.mobile.R;
import com.github.mobile.ui.ProgressDialogTask;
import com.github.mobile.ui.gist.GistsViewActivity;
import com.github.mobile.util.ToastUtils;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.GistService;

import java.util.Collection;

import static com.github.mobile.RequestCodes.GIST_VIEW;

/**
 * Task to open a random Gist
 */
public class RandomGistTask extends ProgressDialogTask<Gist> {

    private static final String TAG = "RandomGistTask";

    @Inject
    private GistService service;

    @Inject
    private GistStore store;

    /**
     * Create task
     *
     * @param context
     */
    public RandomGistTask(final Activity context) {
        super(context);
    }

    /**
     * Execute the task with a progress dialog displaying.
     * <p>
     * This method must be called from the main thread.
     */
    public void start() {
        showIndeterminate(R.string.random_gist);

        execute();
    }

    @Override
    protected Gist run(Account account) throws Exception {
        PageIterator<Gist> pages = service.pagePublicGists(1);
        pages.next();
        int randomPage = 1 + (int) (Math.random() * ((pages.getLastPage() - 1) + 1));

        Collection<Gist> gists = service.pagePublicGists(randomPage, 1).next();

        // Make at least two tries since page numbers are volatile
        if (gists.isEmpty()) {
            randomPage = 1 + (int) (Math.random() * ((pages.getLastPage() - 1) + 1));
            gists = service.pagePublicGists(randomPage, 1).next();
        }

        if (gists.isEmpty())
            throw new IllegalArgumentException(getContext().getString(
                    R.string.no_gists_found));

        return store.addGist(gists.iterator().next());
    }

    @Override
    protected void onSuccess(Gist gist) throws Exception {
        super.onSuccess(gist);

        ((Activity) getContext()).startActivityForResult(
                GistsViewActivity.createIntent(gist), GIST_VIEW);
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception opening random Gist", e);
        ToastUtils.show((Activity) getContext(), e.getMessage());
    }
}
