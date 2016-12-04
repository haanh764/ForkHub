
package com.github.mobile.api.model;

public class Subject {
    public static final String TYPE_ISSUE = "Issue";
    public static final String TYPE_PULL_REQUEST = "PullRequest";
    public static final String TYPE_COMMIT = "Commit";
    public static final String TYPE_RELEASE = "Release";

    public String title;

    public String url;

    public String latest_comment_url;

    public String type;
}
