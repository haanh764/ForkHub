
package com.github.mobile.core.issue;

import com.github.mobile.api.model.ReactionSummary;
import com.github.mobile.api.model.TimelineEvent;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Issue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Issue model with comments
 */
public class FullIssue extends ArrayList<Comment> implements Serializable {

    private static final long serialVersionUID = 4586476132467323827L;

    private final Issue issue;

    private ReactionSummary reactions;

    private Collection<TimelineEvent> events;

    /**
     * Create wrapper for issue, reactions, comments and events
     *
     * @param issue
     * @param reactions
     * @param comments
     * @param events
     */
    public FullIssue(final Issue issue, final ReactionSummary reactions,
            final Collection<Comment> comments, final Collection<TimelineEvent> events) {
        super(comments);

        this.events = events;
        this.reactions = reactions;
        this.issue = issue;
    }

    /**
     * Create empty wrapper
     */
    public FullIssue() {
        this.issue = null;
    }

    /**
     * @return issue
     */
    public Issue getIssue() {
        return issue;
    }

    /**
     * @return reactions
     */
    public ReactionSummary getReactions() {
        return reactions;
    }

    /**
     * @return events
     */
    public Collection<TimelineEvent> getEvents() {
        return events;
    }
}
