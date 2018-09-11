package com.test.githubsearch.di;

import android.app.Application;

import com.test.githubsearch.data.api.GitHubApi;
import com.test.githubsearch.util.Constant;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private Application mApp;

    public ApplicationModule(final Application app) {
        mApp = app;
    }

    @Provides
    Application provideApplication() {
        return mApp;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // add your other interceptors …

        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        return httpClient.build();
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    @Named("github")
    @Inject
    Retrofit provideGitHubApiRetrofit(OkHttpClient httpClient, RxJava2CallAdapterFactory rx2CallAdapter, GsonConverterFactory gsonConverter) {
        return new Retrofit.Builder()
                .baseUrl(Constant.GitHubApi.BASE_URL)
                .addCallAdapterFactory(rx2CallAdapter)
                .addConverterFactory(gsonConverter)
                .client(httpClient)
                .build();
    }

    @Provides
    @Singleton
    @Inject
    GitHubApi provideGitHubApi(@Named("github") Retrofit retrofit) {
        return retrofit.create(GitHubApi.class);
    }

}
