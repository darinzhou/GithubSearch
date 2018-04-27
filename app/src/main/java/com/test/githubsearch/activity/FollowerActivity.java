package com.test.githubsearch.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.test.githubsearch.FollowerContract;
import com.test.githubsearch.FollowerPresenter;
import com.test.githubsearch.R;
import com.test.githubsearch.model.User;
import com.test.githubsearch.util.Constant;

public class FollowerActivity extends AppCompatActivity implements FollowerContract.View {

    FollowerContract.Presenter mPresenter;
    SimpleDraweeView mBackgroundAvarta;
    SimpleDraweeView mAvarta;
    String mFollower;
    ProgressBar mProgressBar;
    TextView mLogin;
    TextView mName;
    TextView mFollowers;
    TextView mFollowing;
    TextView mRepos;
    TextView mFollowersNum;
    TextView mFollowingNum;
    TextView mReposNum;
    TextView mLocation;
    TextView mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_details);

        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("");

        Intent intent = getIntent();
        if (intent != null) {
            mFollower = intent.getStringExtra(Constant.IntentExtra.FOLLOWER_NAME);
        }

        mPresenter = new FollowerPresenter(this, mFollower);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mBackgroundAvarta = (SimpleDraweeView)findViewById(R.id.background);
        mAvarta = (SimpleDraweeView)findViewById(R.id.avarta);

        mLogin = (TextView)findViewById(R.id.tvLogin);
        mName = (TextView)findViewById(R.id.tvName);
        mFollowers = (TextView)findViewById(R.id.tvFollowers);
        mFollowing = (TextView)findViewById(R.id.tvFollowing);
        mRepos = (TextView)findViewById(R.id.tvRespos);
        mFollowersNum = (TextView)findViewById(R.id.tvFollowersNum);
        mFollowingNum = (TextView)findViewById(R.id.tvFollowingNum);
        mReposNum = (TextView)findViewById(R.id.tvResposNum);
        mLocation = (TextView)findViewById(R.id.tvLocation);
        mEmail = (TextView)findViewById(R.id.tvEmail);
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
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
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
    public void showContent(final User user) {
        final Uri uri = Uri.parse(user.avatarUrl);
        mBackgroundAvarta.post(new Runnable() {
            @Override
            public void run() {
                Postprocessor postprocessor = new IterativeBoxBlurPostProcessor(7);
                ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setPostprocessor(postprocessor)
                        .build();
                PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(imageRequest)
                        .setOldController(mBackgroundAvarta.getController())
                        .build();
                mBackgroundAvarta.setController(controller);
                mAvarta.setImageURI(uri);

                mLogin.setText(user.login);
                mName.setText(user.name);
                mFollowersNum.setText(user.followers+"");
                mFollowingNum.setText(user.following+"");
                mReposNum.setText(user.public_repos+"");
                mLocation.setText(user.location);
                mEmail.setText(user.email);

            }
        });
    }
}
