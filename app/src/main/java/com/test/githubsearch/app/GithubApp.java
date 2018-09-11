package com.test.githubsearch.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.test.githubsearch.di.ActivityComponent;
import com.test.githubsearch.di.AppComponent;
import com.test.githubsearch.di.ApplicationModule;
import com.test.githubsearch.di.DaggerAppComponent;

public class GithubApp extends Application {

    private AppComponent mAppComponent;
    private ActivityComponent mActivityComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        createAppComponent();

        Fresco.initialize(this);
    }

    private void createAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public ActivityComponent createActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = mAppComponent.addSubComponent();
        }
        return mActivityComponent;
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    public void releaseActivityComponent() {
        mActivityComponent = null;
    }

}
