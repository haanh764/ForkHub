
package com.github.mobile.api.model;

import com.squareup.moshi.Json;

import java.util.Date;

public class Notification {
    public static final String REASON_SUBSCRIBED = "subscribed";
    public static final String REASON_MANUAL = "manual";
    public static final String REASON_AUTHOR = "author";
    public static final String REASON_COMMENT = "comment";
    public static final String REASON_MENTION = "mention";
    public static final String REASON_TEAM_MENTION = "team_mention";
    public static final String REASON_STATE_CHANGE = "state_change";
    public static final String REASON_ASSIGN = "assign";

    public int id;

    public Repository repository;

    public Subject subject;

    public String reason;

    @Json(name = "unread")
    public boolean is_unread;

    public Date updated_at;

    public Date last_read_at;
}
