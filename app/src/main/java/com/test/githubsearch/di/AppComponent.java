package com.test.githubsearch.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules={ApplicationModule.class})
public interface AppComponent {
    ActivityComponent addSubComponent();
}
