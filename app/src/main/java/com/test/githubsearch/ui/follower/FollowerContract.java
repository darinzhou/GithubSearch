package com.test.githubsearch.ui.follower;

import com.test.githubsearch.base.MvpContract;
import com.test.githubsearch.data.api.model.User;

public interface FollowerContract {
    interface View extends MvpContract.MvpView {
        String getFollowerName();
        void showContent(User user);
    }

    interface Presenter extends MvpContract.MvpPresenter<View> {
    }
}
