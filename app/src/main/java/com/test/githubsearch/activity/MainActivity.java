package com.test.githubsearch.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.test.githubsearch.MainContract;
import com.test.githubsearch.MainPresenter;
import com.test.githubsearch.R;
import com.test.githubsearch.adapter.ResultRecyclerViewAdapter;
import com.test.githubsearch.model.User;
import com.test.githubsearch.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darin on 4/7/2018.
 */

public class MainActivity extends AppCompatActivity implements MainContract.View {

    MainContract.Presenter mPresenter;
    RecyclerView mResultView;
    ResultRecyclerViewAdapter mAdapter;
    List<User> mFollowers = new ArrayList<>();
    String mOwnerName;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");

        Intent intent = getIntent();
        if (intent != null) {
            mOwnerName = intent.getStringExtra(Constant.IntentExtra.OWNER_NAME);
        }

        mPresenter = new MainPresenter(this, mOwnerName);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        // Set the mAdapter
        mResultView = (RecyclerView) findViewById(R.id.recyclerView);
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

        mAdapter = new ResultRecyclerViewAdapter(mFollowers, new ResultRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                String name = user.login;
                Intent intent = new Intent(MainActivity.this, FollowerActivity.class);
                intent.putExtra(Constant.IntentExtra.FOLLOWER_NAME, name);
                startActivity(intent);
            }
        });
        mResultView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void showProgress() {
        mProgressBar.post(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgress() {
        mProgressBar.post(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showError(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateContent(List<User> users) {
        mFollowers.clear();
        mFollowers.addAll(users);
        mResultView.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void displayFollowerDetails(User user) {
        String name = user.login;
        Intent intent = new Intent(MainActivity.this, FollowerActivity.class);
        intent.putExtra(Constant.IntentExtra.FOLLOWER_NAME, name);
        startActivity(intent);
    }
}
