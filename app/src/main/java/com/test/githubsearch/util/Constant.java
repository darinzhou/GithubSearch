package com.test.githubsearch.util;

public class Constant {
    public interface IntentExtra {
        String OWNER_NAME = "com.test.githubsearch.util.OWNER_NAME";
        String FOLLOWER_NAME = "com.test.githubsearch.util.FOLLOWER_NAME";
    }

    public interface GithubApi {
        String BASE_URL = "https://api.github.com/";
        String API_GET_FOLLOWERS = BASE_URL + "users/%s/followers";
        String API_GET_USER = BASE_URL + "users/%s";
    }
}
