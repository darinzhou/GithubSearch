package com.test.githubsearch.api;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import com.test.githubsearch.model.User;

public interface GitHubApi {
    String BASE_URL = "https://api.github.com/";

    @GET("users/{user}")
    Observable<User> getUser(@Path("user") String userName);

    @GET("users/{user}/followers")
    Observable<List<User>> getFollowers(@Path("user") String userName);
}
