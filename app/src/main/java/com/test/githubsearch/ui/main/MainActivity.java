package com.test.githubsearch.ui.main;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.test.githubsearch.R;
import com.test.githubsearch.app.GithubApp;
import com.test.githubsearch.data.api.model.User;
import com.test.githubsearch.ui.follower.FollowerActivity;
import com.test.githubsearch.util.Constant;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Darin on 4/7/2018.
 */

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Inject
    MainPresenter mPresenter;

    private ProgressBar mProgressBar;
    private RecyclerView mResultView;

    private String mOwnerName;
    private FollowersRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Followers");

        //
        // init intent
        //

        Intent intent = getIntent();
        if (intent != null) {
            mOwnerName = intent.getStringExtra(Constant.IntentExtra.OWNER_NAME);
            setTitle("Followers of " + mOwnerName);
        }

        //
        // DI
        //

        ((GithubApp) getApplication()).createActivityComponent().inject(this);

        //
        // UI
        //

        mProgressBar = findViewById(R.id.progressBar);

        mResultView = findViewById(R.id.recyclerView);
        mResultView.setHasFixedSize(true);
        mResultView.setItemAnimator(new DefaultItemAnimator());

        // use grid layout
        final int itemColNum = 3;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, itemColNum);
        mResultView.setLayoutManager(gridLayoutManager);

        // set item decoration
        // to add space between items
        final int spaceBetweenItems = 3;
        mResultView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int right = spaceBetweenItems;
                int top = spaceBetweenItems;
                int itemPosition = parent.getChildAdapterPosition(view) + 1;

                // last col
                if (itemPosition % itemColNum == 0) {
                    right = 0;
                }

                outRect.set(0, top, right, 0);
            }
        });

        //
        // connect view and presenter
        //

        mPresenter.start(this);
        mAdapter = new FollowersRecyclerViewAdapter(mPresenter);
        mResultView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.stop();
    }

    //--------------------------------------------------------------------------------------------
    // implements methods of MainContract.View
    //--------------------------------------------------------------------------------------------

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(final String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUserName() {
        return mOwnerName;
    }

    @Override
    public void updateFollowers() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayFollowerDetails(String followerName) {
        Intent intent = new Intent(MainActivity.this, FollowerActivity.class);
        intent.putExtra(Constant.IntentExtra.FOLLOWER_NAME, followerName);
        startActivity(intent);
    }
}
