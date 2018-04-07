package com.test.githubsearch;

import com.test.githubsearch.model.User;
import com.test.githubsearch.util.Constant;
import com.test.githubsearch.util.GithubApi;

import java.util.List;

public class MainPresenter implements MainContract.Presenter, GithubApi.ApiCallback {

    MainContract.View view;
    String ownerName;

    public MainPresenter(MainContract.View view, String ownerName) {
        this.view = view;
        this.ownerName = ownerName;
    }

    @Override
    public void onResume() {
        view.showProgress();
        String api = String.format(Constant.GithubApi.API_GET_FOLLOWERS, ownerName);
        GithubApi.excute(api, this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onItemClick(User user) {
        view.displayFollowerDetails(user);
    }

    @Override
    public void onSucceed(List<User> users) {
        view.updateContent(users);
        view.hideProgress();
    }

    @Override
    public void onFailed(String error) {
        view.hideProgress();
        view.showError(error);
    }
}
