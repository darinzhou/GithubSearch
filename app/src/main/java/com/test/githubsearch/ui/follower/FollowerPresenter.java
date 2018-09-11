package com.test.githubsearch.ui.follower;

import com.test.githubsearch.data.api.GitHubService;
import com.test.githubsearch.data.api.model.User;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FollowerPresenter implements FollowerContract.Presenter {

    private FollowerContract.View mView;

    private CompositeDisposable mCompositeDisposable;

    private GitHubService mGitHubService;

    @Inject
    public FollowerPresenter(GitHubService gitHubService) {
        mGitHubService = gitHubService;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void start(FollowerContract.View view) {
        mView = view;
        getUser(mView.getFollowerName());
    }

    @Override
    public void stop() {
        mCompositeDisposable.clear();
    }

    private void getUser(String userName) {
        mView.showProgress();
        DisposableObserver<User> userObserver = new DisposableObserver<User>() {

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
            public void onNext(User user) {
                mView.showContent(user);
            }
        };

        mCompositeDisposable.add(mGitHubService.getUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(userObserver));
    }

}
