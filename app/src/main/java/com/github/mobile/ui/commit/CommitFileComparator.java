
package com.github.mobile.ui.commit;

import org.eclipse.egit.github.core.CommitFile;

import java.util.Comparator;

/**
 * Comparator for commit files
 */
public class CommitFileComparator implements Comparator<CommitFile> {

    @Override
    public int compare(final CommitFile lhs, final CommitFile rhs) {
        String lPath = lhs.getFilename();
        final int lSlash = lPath.lastIndexOf('/');
        if (lSlash != -1)
            lPath = lPath.substring(lSlash + 1);

        String rPath = rhs.getFilename();
        final int rSlash = rPath.lastIndexOf('/');
        if (rSlash != -1)
            rPath = rPath.substring(rSlash + 1);

        return lPath.compareTo(rPath);
    }
}
