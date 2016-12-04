
package com.github.mobile.core.commit;

import com.github.mobile.core.ResourcePager;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommit;

/**
 * Pager over commits
 */
public abstract class CommitPager extends ResourcePager<RepositoryCommit> {

    private final IRepositoryIdProvider repository;

    private final CommitStore store;

    /**
     * Create pager
     *
     * @param repository
     * @param store
     */
    public CommitPager(final IRepositoryIdProvider repository,
            final CommitStore store) {
        this.repository = repository;
        this.store = store;
    }

    @Override
    protected Object getId(final RepositoryCommit resource) {
        return resource.getSha();
    }

    @Override
    protected RepositoryCommit register(final RepositoryCommit resource) {
        return store.addCommit(repository, resource);
    }
}
