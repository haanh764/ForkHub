
package com.github.mobile.core.repo;

import android.content.Intent;

import com.github.mobile.core.user.UserUriMatcher;
import com.github.mobile.ui.repo.RepositoryViewActivity;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

import java.util.List;

/**
 * Parses a {@link Repository} from a path
 */
public class RepositoryUriMatcher {

    /**
     * Get the repo for the given path
     *
     * @param pathSegments
     * @return {@link Repository} or null if path is not valid
     */
    public static Repository getRepository(List<String> pathSegments) {
        if (pathSegments.size() < 2)
            return null;

        User user = UserUriMatcher.getUser(pathSegments);
        if (user == null)
            return null;

        String repoName = pathSegments.get(1);
        if (!RepositoryUtils.isValidRepo(repoName))
            return null;

        Repository repository = new Repository();
        repository.setName(repoName);
        repository.setOwner(user);
        return repository;
    }

    /**
     * Get an intent for an exact {@link Repository} match
     *
     * @param pathSegments
     * @return {@link Intent} or null if path is not valid
     */
    public static Intent getRepositoryIntent(List<String> pathSegments) {
        if (pathSegments.size() != 2 && pathSegments.size() != 3)
            return null;

        Repository repo = getRepository(pathSegments);
        if (repo == null)
            return null;

        if (pathSegments.size() == 2)
            return RepositoryViewActivity.createIntent(repo);

        if (!"issues".equals(pathSegments.get(2)) && !"pulls".equals(pathSegments.get(2)))
            return null;

        return RepositoryViewActivity.createIntentForIssues(repo);
    }
}
