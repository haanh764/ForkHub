
package com.github.mobile.core.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.event.Event;
import org.eclipse.egit.github.core.event.EventPayload;
import org.eclipse.egit.github.core.event.GistPayload;

import static org.eclipse.egit.github.core.event.Event.TYPE_GIST;

/**
 * Helper to find a {@link Gist} to open for an event
 */
public class GistEventMatcher {

    /**
     * Get gist from event
     *
     * @param event
     * @return gist or null if event doesn't apply
     */
    public static Gist getGist(final Event event) {
        if (event == null)
            return null;
        EventPayload payload = event.getPayload();
        if (payload == null)
            return null;
        String type = event.getType();
        if (TYPE_GIST.equals(type))
            return ((GistPayload) payload).getGist();
        else
            return null;
    }
}
