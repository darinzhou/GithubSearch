package com.test.githubsearch;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class GithubApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
