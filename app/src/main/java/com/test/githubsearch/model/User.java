package com.test.githubsearch.model;

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

    public int public_repos;

    public String login;
    public String name;
    public String location;
    public String email;
    public int followers;
    public int following;

    public User() {}

    public User(String url, String login){
        this.avatarUrl = url;
        this.login = login;
    }

    public static User fromJson(JSONObject json) {
        User user = new User();
        if (json == null) {
            return user;
        }

        if (json.has("login")) {
            try {
                user.login = json.getString("login");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (json.has("avatar_url")) {
            try {
                user.avatarUrl = json.getString("avatar_url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (json.has("name")) {
            try {
                user.name = json.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (json.has("location")) {
            try {
                user.location = json.getString("location");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (json.has("email")) {
            try {
                user.email = json.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (json.has("public_repos")) {
            try {
                user.public_repos = json.getInt("public_repos");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (json.has("followers")) {
            try {
                user.followers = json.getInt("followers");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (json.has("following")) {
            try {
                user.following = json.getInt("following");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return user;
    }
}
