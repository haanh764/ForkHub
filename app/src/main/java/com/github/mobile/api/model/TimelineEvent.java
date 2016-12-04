
package com.github.mobile.api.model;

import java.util.Date;

public class TimelineEvent {
    public static final String EVENT_ASSIGNED = "assigned";
    public static final String EVENT_CLOSED = "closed";
    public static final String EVENT_COMMENTED = "commented";
    public static final String EVENT_COMMITTED = "committed";
    public static final String EVENT_CROSS_REFERENCED = "cross-referenced";
    public static final String EVENT_DEMILESTONED = "demilestoned";
    public static final String EVENT_HEAD_REF_DELETED = "head_ref_deleted";
    public static final String EVENT_HEAD_REF_RESTORED = "head_ref_restored";
    public static final String EVENT_LABELED = "labeled";
    public static final String EVENT_LOCKED = "locked";
    public static final String EVENT_MENTIONED = "mentioned";
    public static final String EVENT_MERGED = "merged";
    public static final String EVENT_MILESTONED = "milestoned";
    public static final String EVENT_REFERENCED = "referenced";
    public static final String EVENT_RENAMED = "renamed";
    public static final String EVENT_REOPENED = "reopened";
    public static final String EVENT_SUBSCRIBED = "subscribed";
    public static final String EVENT_UNASSIGNED = "unassigned";
    public static final String EVENT_UNLABELED = "unlabeled";
    public static final String EVENT_UNLOCKED = "unlocked";
    public static final String EVENT_UNSUBSCRIBED = "unsubscribed";

    public long id;

    public User actor;

    public String commit_id;

    public String event;

    public Date created_at;

    public Date updated_at;

    public Label label;

    public User assignee;

    public Milestone milestone;

    public Rename rename;
}
