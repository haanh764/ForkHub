
package com.github.mobile.core.commit;

import android.text.TextUtils;

import org.eclipse.egit.github.core.CommitComment;
import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.RepositoryCommit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Commit model with comments
 */
public class FullCommit extends ArrayList<CommitComment> implements
        Serializable {

    private static final long serialVersionUID = 2470370479577730822L;

    private final RepositoryCommit commit;

    private final List<FullCommitFile> files;

    /**
     * Create commit with no comments
     *
     * @param commit
     */
    public FullCommit(final RepositoryCommit commit) {
        this.commit = commit;
        List<CommitFile> rawFiles = commit.getFiles();
        if (rawFiles != null && !rawFiles.isEmpty()) {
            files = new ArrayList<FullCommitFile>(rawFiles.size());
            for (CommitFile file : rawFiles)
                files.add(new FullCommitFile(file));
        } else
            files = Collections.emptyList();
    }

    /**
     * Create commit with comments
     *
     * @param commit
     * @param comments
     */
    public FullCommit(final RepositoryCommit commit,
            final Collection<CommitComment> comments) {
        this.commit = commit;

        List<CommitFile> rawFiles = commit.getFiles();
        boolean hasComments = comments != null && !comments.isEmpty();
        boolean hasFiles = rawFiles != null && !rawFiles.isEmpty();
        if (hasFiles) {
            files = new ArrayList<FullCommitFile>(rawFiles.size());
            if (hasComments) {
                for (CommitFile file : rawFiles) {
                    Iterator<CommitComment> iterator = comments.iterator();
                    FullCommitFile full = new FullCommitFile(file);
                    while (iterator.hasNext()) {
                        CommitComment comment = iterator.next();
                        if (file.getFilename().equals(comment.getPath())) {
                            full.add(comment);
                            iterator.remove();
                        }
                    }
                    files.add(full);
                }
                hasComments = !comments.isEmpty();
            } else
                for (CommitFile file : rawFiles)
                    files.add(new FullCommitFile(file));
        } else
            files = Collections.emptyList();

        if (hasComments)
            addAll(comments);
    }

    @Override
    public boolean add(final CommitComment comment) {
        String path = comment.getPath();
        if (TextUtils.isEmpty(path))
            return super.add(comment);
        else {
            boolean added = false;
            for (FullCommitFile file : files)
                if (path.equals(file.getFile().getFilename())) {
                    file.add(comment);
                    added = true;
                    break;
                }
            if (!added)
                added = super.add(comment);
            return added;
        }
    }

    /**
     * @return files
     */
    public List<FullCommitFile> getFiles() {
        return files;
    }

    /**
     * @return commit
     */
    public RepositoryCommit getCommit() {
        return commit;
    }
}
