
package com.github.mobile.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class PageIterator<V> implements Iterator<Collection<V>> {
    private final int itemsPerPage;

    private int nextPage;

    public PageIterator(int initialPage, int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        this.nextPage = initialPage;
    }

    @Override
    public boolean hasNext() {
        return this.nextPage != -1;
    }

    @Override
    public Collection<V> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        Collection<V> resources = getPage(nextPage, itemsPerPage);

        if (resources.size() == 0) {
            nextPage = -1;
        } else {
            ++nextPage;
        }

        return resources;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }

    protected abstract Collection<V> getPage(int page, int itemsPerPage);
}
