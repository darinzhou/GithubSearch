package com.test.githubsearch;

import com.test.githubsearch.api.GitHubClient;
import com.test.githubsearch.model.User;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FollowerPresenter implements FollowerContract.Presenter {

    FollowerContract.View view;
    String followerName;
    Subscription subscription;

    public FollowerPresenter(FollowerContract.View view, String followerName) {
        this.view = view;
        this.followerName = followerName;
    }

    @Override
    public void onResume() {
        getUser(followerName);
    }

    private void getUser(String userName) {
        view.showProgress();
        Observer<User> userObserver = new Observer<User>() {
            @Override
            public void onCompleted() {
                view.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                view.hideProgress();
                view.showError(e.getMessage());
            }

            @Override
            public void onNext(User user) {
                view.showContent(user);
            }
        };

        subscription = GitHubClient.getInstance().getUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userObserver);
    }

    @Override
    public void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

}
