package com.test.githubsearch.di;

import com.test.githubsearch.ui.follower.FollowerActivity;
import com.test.githubsearch.ui.main.MainActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(FollowerActivity activity);
}
