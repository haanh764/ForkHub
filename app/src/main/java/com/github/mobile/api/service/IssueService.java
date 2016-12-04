
package com.github.mobile.api.service;

import com.github.mobile.api.model.Issue;
import com.github.mobile.api.model.TimelineEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IssueService {
    @Headers("Accept: application/vnd.github.squirrel-girl-preview")
    @GET("repos/{owner}/{repo}/issues/{number}")
    Call<Issue> getIssue(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("number") int number);

    @Headers("Accept: application/vnd.github.mockingbird-preview")
    @GET("repos/{owner}/{repo}/issues/{issue_number}/timeline")
    Call<List<TimelineEvent>> getTimeline(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Path("issue_number") long issue_number,
            @Query("page") int page,
            @Query("per_page") int per_page);
}
