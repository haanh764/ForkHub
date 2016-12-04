
package com.github.mobile.api.model;

import com.squareup.moshi.Json;

import java.util.List;

public class SearchResult<V> {
    public int total_count;

    @Json(name = "incomplete_results")
    public boolean has_incomplete_results;

    public List<V> items;
}
