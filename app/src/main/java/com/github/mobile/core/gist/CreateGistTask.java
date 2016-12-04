
package com.github.mobile.core.gist;

import android.accounts.Account;
import android.app.Activity;
import android.util.Log;

import com.github.mobile.R;
import com.github.mobile.ui.ProgressDialogTask;
import com.github.mobile.util.ToastUtils;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;

import java.util.Collections;

/**
 * Task to create a {@link Gist}
 */
public class CreateGistTask extends ProgressDialogTask<Gist> {

    private static final String TAG = "CreateGistTask";

    @Inject
    private GistService service;

    private final String description;

    private final boolean isPublic;

    private final String name;

    private final String content;

    /**
     * Create task that creates a {@link Gist}
     *
     * @param activity
     * @param description
     * @param isPublic
     * @param name
     * @param content
     */
    public CreateGistTask(Activity activity, String description,
            boolean isPublic, String name, String content) {
        super(activity);

        this.description = description;
        this.isPublic = isPublic;
        this.name = name;
        this.content = content;
    }

    @Override
    public Gist run(Account account) throws Exception {
        Gist gist = new Gist();
        gist.setDescription(description);
        gist.setPublic(isPublic);

        GistFile file = new GistFile();
        file.setContent(content);
        file.setFilename(name);
        gist.setFiles(Collections.singletonMap(name, file));

        return service.createGist(gist);
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception creating Gist", e);
        ToastUtils.show((Activity) getContext(), e.getMessage());
    }

    /**
     * Create the {@link Gist} with the configured values
     */
    public void create() {
        showIndeterminate(R.string.creating_gist);

        execute();
    }
}
