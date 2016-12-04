
package com.github.mobile.api.model;

import com.squareup.moshi.Json;

public class ReactionSummary {
    public int total_count;

    @Json(name = "+1")
    public int plus_one;

    @Json(name = "-1")
    public int minus_one;

    public int laugh;

    public int confused;

    public int heart;

    public int hooray;
}
