package com.test.githubsearch;

import com.test.githubsearch.api.GitHubClient;
import com.test.githubsearch.model.User;

import java.util.List;
import rx.Observer;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    MainContract.View view;
    String ownerName;
    Subscription subscription;

    public MainPresenter(MainContract.View view, String ownerName) {
        this.view = view;
        this.ownerName = ownerName;
    }

    @Override
    public void onResume() {
        getFollowers(ownerName);
    }

    private void getFollowers(String userName) {
        view.showProgress();
        Observer<List<User>> followersObserver = new Observer<List<User>>() {
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
            public void onNext(List<User> users) {
                view.updateContent(users);
            }
        };

        subscription = GitHubClient.getInstance().getFollowers(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followersObserver);
    }

    @Override
    public void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onItemClick(User user) {
        view.displayFollowerDetails(user);
    }

}
