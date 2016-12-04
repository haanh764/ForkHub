
package com.github.mobile.core.commit;

import android.util.SparseArray;

import org.eclipse.egit.github.core.CommitComment;
import org.eclipse.egit.github.core.CommitFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Commit file with comments
 */
public class FullCommitFile {

    private final SparseArray<List<CommitComment>> comments = new SparseArray<List<CommitComment>>(
            4);

    private final CommitFile file;

    /**
     * Create file
     *
     * @param file
     */
    public FullCommitFile(final CommitFile file) {
        this.file = file;
    }

    /**
     * Get comments for line
     *
     * @param line
     * @return comments
     */
    public List<CommitComment> get(final int line) {
        List<CommitComment> lineComments = comments.get(line);
        return lineComments != null ? lineComments : Collections
                .<CommitComment> emptyList();
    }

    /**
     * Add comment to file
     *
     * @param comment
     * @return this file
     */
    public FullCommitFile add(final CommitComment comment) {
        int line = comment.getPosition();
        if (line >= 0) {
            List<CommitComment> lineComments = comments.get(line);
            if (lineComments == null) {
                lineComments = new ArrayList<CommitComment>(4);
                comments.put(line, lineComments);
            }
            lineComments.add(comment);
        }
        return this;
    }

    /**
     * @return file
     */
    public CommitFile getFile() {
        return file;
    }
}
