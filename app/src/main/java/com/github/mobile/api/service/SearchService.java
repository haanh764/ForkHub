
package com.github.mobile.api.service;

import com.github.mobile.api.model.Repository;
import com.github.mobile.api.model.SearchResult;
import com.github.mobile.api.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {
    @GET("search/users")
    Call<SearchResult<User>> searchUsers(
            @Query("q") String q,
            @Query("page") int page);

    @GET("search/repositories")
    Call<SearchResult<Repository>> searchRepositories(
            @Query("q") String q,
            @Query("page") int page);
}
