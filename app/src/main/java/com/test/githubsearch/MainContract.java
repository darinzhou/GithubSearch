package com.test.githubsearch;

import com.test.githubsearch.model.User;

import java.util.List;

public interface MainContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showError(String error);
        void updateContent(List<User> users);
        void displayFollowerDetails(User user);
    }

    interface Presenter {
        void onResume();
        void onDestroy();
        void onItemClick(User user);
    }
}
