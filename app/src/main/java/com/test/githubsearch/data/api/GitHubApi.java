package com.test.githubsearch.data.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.test.githubsearch.data.api.model.User;

public interface GitHubApi {
    String BASE_URL = "https://api.github.com/";

    @GET("users/{user}")
    Observable<User> getUser(@Path("user") String userName);

    @GET("users/{user}/followers")
    Observable<List<User>> getFollowers(@Path("user") String userName);
}
