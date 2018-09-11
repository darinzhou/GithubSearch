package com.test.githubsearch.data.api;

import android.support.annotation.NonNull;

import com.test.githubsearch.data.api.model.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class GitHubService {

    private GitHubApi mApi;

    @Inject
    public GitHubService(GitHubApi api) {
        mApi = api;
    }

    public Observable<User> getUser(@NonNull String userName) {
        return mApi.getUser(userName);
    }

    public Observable<List<User>> getFollowers(@NonNull String userName) {
        return mApi.getFollowers(userName);
    }
}
