
package com.github.mobile.core.gist;

import android.content.Intent;
import android.text.TextUtils;

import com.github.mobile.core.commit.CommitUtils;
import com.github.mobile.ui.gist.GistsViewActivity;

import org.eclipse.egit.github.core.Gist;

import java.util.List;

/**
 * Parses a {@link Gist} from a path
 */
public class GistUriMatcher {

    /**
     * Get an intent for an exact {@link Gist} match
     *
     * @param pathSegments
     * @return {@link Intent} or null if path is not valid
     */
    public static Intent getGistIntent(List<String> pathSegments) {
        if (pathSegments.size() != 1 && pathSegments.size() != 2)
            return null;

        String gistId = pathSegments.get(pathSegments.size()-1);
        if (TextUtils.isEmpty(gistId))
            return null;

        if (TextUtils.isDigitsOnly(gistId))
            return GistsViewActivity.createIntent(new Gist().setId(gistId));

        if (CommitUtils.isValidCommit(gistId))
            return GistsViewActivity.createIntent(new Gist().setId(gistId));

        return null;
    }
}
