
package com.github.mobile.core.gist;

import com.github.mobile.core.ResourcePager;

import org.eclipse.egit.github.core.Gist;

/**
 * Pager over Gists
 */
public abstract class GistPager extends ResourcePager<Gist> {

    private final GistStore store;

    /**
     * Create pager
     *
     * @param store
     */
    public GistPager(final GistStore store) {
        this.store = store;
    }

    @Override
    protected Object getId(Gist resource) {
        return resource.getId();
    }

    @Override
    protected Gist register(Gist resource) {
        return store.addGist(resource);
    }
}
