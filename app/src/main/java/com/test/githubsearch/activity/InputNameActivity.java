package com.test.githubsearch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.test.githubsearch.R;
import com.test.githubsearch.util.Constant;

public class InputNameActivity extends AppCompatActivity {

    EditText mEtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);

        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.layout_title_bar);

       mEtName = (EditText) findViewById(R.id.etName);
        mEtName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    startMainActivity();
                }
                return false;
            }
        });
    }

    protected void startMainActivity() {
        String name = mEtName.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constant.IntentExtra.OWNER_NAME, name);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startMainActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
