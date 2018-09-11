package com.test.githubsearch.ui.main;

import android.net.Uri;

import com.test.githubsearch.base.MvpContract;
import com.test.githubsearch.data.api.model.User;

public interface MainContract {
    interface View extends MvpContract.MvpView {
        String getUserName();
        void updateFollowers();
        void displayFollowerDetails(String followerName);
    }

    interface FollowerItemView {
        void setName(String name);
        void displayAvarta(Uri imageUri);
    }

    interface Presenter extends MvpContract.MvpPresenter<View> {
        void onFollowerItemClick(int position);
        int getFollowerCount();
        void onBindFollowerItemView(FollowerItemView viewHolder, int position);
    }
}
