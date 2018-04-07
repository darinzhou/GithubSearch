package com.test.githubsearch;

import com.test.githubsearch.model.User;
import com.test.githubsearch.util.Constant;
import com.test.githubsearch.util.GithubApi;

import java.util.List;

public class FollowerPresenter implements FollowerContract.Presenter, GithubApi.ApiCallback {

    FollowerContract.View view;
    String followerName;

    public FollowerPresenter(FollowerContract.View view, String followerName) {
        this.view = view;
        this.followerName = followerName;
    }

    @Override
    public void onResume() {
        view.showProgress();
        String api = String.format(Constant.GithubApi.API_GET_USER, followerName);
        GithubApi.excute(api, this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSucceed(List<User> users) {
        view.showContent(users.get(0));
        view.hideProgress();
    }

    @Override
    public void onFailed(String error) {
        view.showError(error);
        view.hideProgress();
    }

}
