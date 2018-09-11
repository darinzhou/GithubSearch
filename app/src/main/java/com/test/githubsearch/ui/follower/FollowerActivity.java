package com.test.githubsearch.ui.follower;

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
import com.test.githubsearch.R;
import com.test.githubsearch.app.GithubApp;
import com.test.githubsearch.data.api.model.User;
import com.test.githubsearch.util.Constant;

import javax.inject.Inject;

public class FollowerActivity extends AppCompatActivity implements FollowerContract.View {

    @Inject
    FollowerPresenter mPresenter;

    private String mFollowerName;

    private SimpleDraweeView mBackgroundAvarta;
    private SimpleDraweeView mAvarta;
    private ProgressBar mProgressBar;
    private TextView mLogin;
    private TextView mName;
    private TextView mFollowersNum;
    private TextView mFollowingNum;
    private TextView mReposNum;
    private TextView mLocation;
    private TextView mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_details);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle("");

        //
        // init intent
        //

        Intent intent = getIntent();
        if (intent != null) {
            mFollowerName = intent.getStringExtra(Constant.IntentExtra.FOLLOWER_NAME);
        }

        //
        // DI
        //

        ((GithubApp) getApplication()).createActivityComponent().inject(this);

        //
        // UI
        //

        mProgressBar = findViewById(R.id.progressBar);
        mBackgroundAvarta = findViewById(R.id.background);
        mAvarta = findViewById(R.id.avarta);

        mLogin = findViewById(R.id.tvLogin);
        mName = findViewById(R.id.tvName);
        mFollowersNum = findViewById(R.id.tvFollowersNum);
        mFollowingNum = findViewById(R.id.tvFollowingNum);
        mReposNum = findViewById(R.id.tvResposNum);
        mLocation = findViewById(R.id.tvLocation);
        mEmail = findViewById(R.id.tvEmail);

        //
        // connect view and presenter
        //

        mPresenter.start(this);
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
        ((GithubApp) getApplication()).releaseActivityComponent();
    }

    //--------------------------------------------------------------------------------------------
    // implements methods of FollowerContract.View
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
    public String getFollowerName() {
        return mFollowerName;
    }

    @Override
    public void showContent(final User user) {
        final Uri uri = Uri.parse(user.avatarUrl);
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
        mFollowersNum.setText(user.followers + "");
        mFollowingNum.setText(user.following + "");
        mReposNum.setText(user.publicRepos + "");
        mLocation.setText(user.location);
        mEmail.setText(user.email);
    }
}
