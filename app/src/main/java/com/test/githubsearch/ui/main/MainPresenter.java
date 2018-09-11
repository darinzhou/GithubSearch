package com.test.githubsearch.ui.main;

import android.net.Uri;

import com.test.githubsearch.data.api.GitHubService;
import com.test.githubsearch.data.api.model.User;
import com.test.githubsearch.di.PerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@PerActivity
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    private List<User> mFollowers;
    private CompositeDisposable mCompositeDisposable;

    private GitHubService mGitHubService;

    @Inject
    public MainPresenter(GitHubService gitHubService) {
        mGitHubService = gitHubService;
        mFollowers = new ArrayList<>();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void start(MainContract.View view) {
        mView = view;
        retrieveFollowers(mView.getUserName());
    }

    @Override
    public void stop() {
        mCompositeDisposable.dispose();
    }

    private void retrieveFollowers(String userName) {
        mView.showProgress();
        DisposableObserver<List<User>> followersObserver = new DisposableObserver<List<User>>() {
            @Override
            public void onError(Throwable e) {
                mView.hideProgress();
                mView.showError(e.getMessage());
            }

            @Override
            public void onComplete() {
                mView.hideProgress();
            }

            @Override
            public void onNext(List<User> users) {
                mFollowers = users;
                mView.updateFollowers();
            }
        };

        mCompositeDisposable.add(mGitHubService.getFollowers(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(followersObserver));
    }

    @Override
    public int getFollowerCount() {
        return mFollowers.size();
    }

    @Override
    public void onBindFollowerItemView(MainContract.FollowerItemView viewHolder, int position) {
        User follower = mFollowers.get(position);

        viewHolder.setName(follower.login);

        Uri imageUri = Uri.parse(follower.avatarUrl);
        viewHolder.displayAvarta(imageUri);
    }

    @Override
    public void onFollowerItemClick(int position) {
        mView.displayFollowerDetails(mFollowers.get(position).login);
    }

}
