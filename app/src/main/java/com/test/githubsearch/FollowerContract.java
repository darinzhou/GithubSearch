package com.test.githubsearch;

import com.test.githubsearch.model.User;

import java.util.List;

public interface FollowerContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showError(String error);
        void showContent(User user);
    }

    interface Presenter {
        void onResume();
        void onDestroy();
    }
}
