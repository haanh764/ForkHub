
package com.github.mobile.api.service;

import com.github.mobile.api.PageIterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public abstract class PaginationService<V> {
    public static final int ITEMS_PER_PAGE_DEFAULT = 30;
    public static final int ITEMS_PER_PAGE_MAX = 100;

    private final int initialPage;
    private final int itemsPerPage;

    public PaginationService() {
        this.initialPage = 1;
        this.itemsPerPage = ITEMS_PER_PAGE_DEFAULT;
    }

    public PaginationService(int initialPage) {
        if (initialPage < 1) {
            this.initialPage = 1;
        } else {
            this.initialPage = initialPage;
        }

        this.itemsPerPage = ITEMS_PER_PAGE_DEFAULT;
    }

    public PaginationService(int initialPage, int itemsPerPage) {
        if (initialPage < 1) {
            this.initialPage = 1;
        } else {
            this.initialPage = initialPage;
        }

        if (itemsPerPage <= 0) {
            this.itemsPerPage = ITEMS_PER_PAGE_DEFAULT;
        } else if (itemsPerPage > ITEMS_PER_PAGE_MAX) {
            this.itemsPerPage = ITEMS_PER_PAGE_MAX;
        } else {
            this.itemsPerPage = itemsPerPage;
        }
    }

    public abstract Collection<V> getSinglePage(int page, int itemsPerPage) throws IOException;

    public PageIterator getIterator() {
        return new PageIterator<V>(initialPage, itemsPerPage) {

            @Override
            protected Collection<V> getPage(int page, int itemsPerPage) {
                try {
                    return getSinglePage(page, itemsPerPage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return new ArrayList<>(0);
            }
        };
    }

    public Collection<V> getAll(PageIterator<V> iterator) {
        Collection<V> result = new ArrayList<>(itemsPerPage);
        while (iterator.hasNext()) {
            result.addAll(iterator.next());
        }
        return result;
    }

    public Collection<V> getAll() {
        return getAll(getIterator());
    }
}
