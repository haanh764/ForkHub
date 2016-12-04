
package com.github.mobile.api.model;

import com.squareup.moshi.Json;

public class Repository {
    public long id;

    public User owner;

    public String name;

    public String full_name;

    public String description;

    @Json(name = "private")
    public boolean is_private;

    @Json(name = "fork")
    public boolean is_fork;

    public String created_at;

    public String updated_at;

    public String pushed_at;

    public String homepage;

    public int size;

    public int stargazers_count;

    public int watchers_count;

    public String language;

    public boolean has_issues;

    public boolean has_downloads;

    public boolean has_wiki;

    public boolean has_pages;

    public int forks_count;

    public String mirror_url;

    public int open_issues_count;

    public int forks;

    public int open_issues;

    public int watchers;

    public String default_branch;

    public int network_count;

    public int subscribers_count;

    public Permissions permissions;

    public User organization;

    public Repository parent;

    public Repository source;
}
