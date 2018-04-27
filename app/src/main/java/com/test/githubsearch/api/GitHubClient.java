package com.test.githubsearch.api;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import com.test.githubsearch.model.User;

public class GitHubClient {
    private static GitHubClient instance;
    private GitHubApi api;

    private GitHubClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(GitHubApi.class);
    }

    public static GitHubClient getInstance() {
        if (instance == null) {
            instance = new GitHubClient();
        }
        return instance;
    }

    public Observable<User> getUser(@NonNull String userName) {
        return api.getUser(userName);
    }

    public Observable<List<User>> getFollowers(@NonNull String userName) {
        return api.getFollowers(userName);
    }
}
