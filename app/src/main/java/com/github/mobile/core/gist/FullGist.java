
package com.github.mobile.core.gist;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Gist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Gist model with comments and starred status
 */
public class FullGist extends ArrayList<Comment> implements Serializable {

    private static final long serialVersionUID = -5966699489498437000L;

    private final Gist gist;

    private final boolean starred;

    /**
     * Create gist with comments
     *
     * @param gist
     * @param starred
     * @param comments
     */
    public FullGist(final Gist gist, final boolean starred,
            final Collection<Comment> comments) {
        super(comments);

        this.starred = starred;
        this.gist = gist;
    }

    /**
     * Create empty gist
     */
    public FullGist() {
        this.gist = null;
        this.starred = false;
    }

    /**
     * @return starred
     */
    public boolean isStarred() {
        return starred;
    }

    /**
     * @return gist
     */
    public Gist getGist() {
        return gist;
    }
}
