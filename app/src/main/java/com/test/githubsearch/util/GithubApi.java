package com.test.githubsearch.util;

import com.test.githubsearch.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GithubApi {

    public interface ApiCallback {
        void onSucceed(List<User> users);
        void onFailed(String error);
    }
    public static void excute(String apiMethod, final ApiCallback apiCallback) {
        try {
            ConnectionSpec spec =  new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .build();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectionSpecs(Collections.singletonList(spec))
                    .build();

            Request request = new Request.Builder()
                    .url(apiMethod)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    if (apiCallback != null) {
                        apiCallback.onFailed(e.getMessage());
                    }
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful()) {
                            String error = "Unexpected code " + response;
                            if (apiCallback != null) {
                                apiCallback.onFailed(error);
                            }
                            throw new IOException(error);
                        }

                        String data = responseBody.string();
                        List<User> users = new ArrayList<>();
                        try {
                            Object json = new JSONTokener(data).nextValue();
                            if (json instanceof JSONObject) {
                                JSONObject item = (JSONObject)json;
                                users.add(User.fromJson(item));
                            } else if (json instanceof JSONArray) {
                                JSONArray items = (JSONArray)json;
                                for (int i = 0; i < items.length(); ++i) {
                                    JSONObject item = items.getJSONObject(i);
                                    users.add(User.fromJson(item));
                                }
                            }
                            if (apiCallback != null) {
                                apiCallback.onSucceed(users);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (apiCallback != null) {
                                apiCallback.onFailed(e.getMessage());
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (apiCallback != null) {
                apiCallback.onFailed(e.getMessage());
            }
        }
    }

}
