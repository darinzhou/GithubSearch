package com.test.githubsearch.data.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Darin on 4/7/2018.
 */

public class User {
    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;

    @SerializedName("public_repos")
    @Expose
    public int publicRepos;

    public String login;
    public String name;
    public String location;
    public String email;
    public int followers;
    public int following;
}
