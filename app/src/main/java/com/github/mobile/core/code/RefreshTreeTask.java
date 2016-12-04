
package com.github.mobile.core.code;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.github.mobile.core.ref.RefUtils;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.Reference;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.Tag;
import org.eclipse.egit.github.core.Tree;
import org.eclipse.egit.github.core.service.DataService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;

/**
 * Task to load the tree for a repository's default branch
 */
public class RefreshTreeTask extends AuthenticatedUserTask<FullTree> {

    private static final String TAG = "RefreshTreeTask";

    private final Repository repository;

    private final Reference reference;

    @Inject
    private RepositoryService repoService;

    @Inject
    private DataService dataService;

    /**
     * Create task to refresh repository's tree
     *
     * @param repository
     * @param reference
     * @param context
     */
    public RefreshTreeTask(final Repository repository,
            final Reference reference, final Context context) {
        super(context);

        this.repository = repository;
        this.reference = reference;
    }

    private boolean isValidRef(Reference ref) {
        return ref != null && ref.getObject() != null
                && !TextUtils.isEmpty(ref.getObject().getSha());
    }

    @Override
    protected FullTree run(Account account) throws Exception {
        Reference ref = reference;
        String branch = RefUtils.getPath(ref);
        if (branch == null) {
            branch = repository.getDefaultBranch();
            if (TextUtils.isEmpty(branch)) {
                branch = repoService.getRepository(repository)
                        .getDefaultBranch();
                if (TextUtils.isEmpty(branch))
                    throw new IOException(
                            "Repository does not have master branch");
            }
            branch = "heads/" + branch;
        }

        if (!isValidRef(ref)) {
            ref = dataService.getReference(repository, branch);
            if (!isValidRef(ref))
                throw new IOException(
                        "Reference does not have associated commit SHA-1");
        }

        if (RefUtils.isAnnotatedTag(ref)) {
            Tag tag = dataService.getTag(repository, ref.getObject().getSha());
            if (!TextUtils.isEmpty(tag.getObject().getSha())) {
                ref.setObject(tag.getObject());
            }
        }

        Commit commit = dataService.getCommit(repository, ref.getObject()
                .getSha());
        if (commit == null || commit.getTree() == null
                || TextUtils.isEmpty(commit.getTree().getSha()))
            throw new IOException("Commit does not have associated tree SHA-1");

        Tree tree = dataService.getTree(repository, commit.getTree().getSha(),
                true);
        return new FullTree(tree, ref);
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception loading tree", e);
    }
}
