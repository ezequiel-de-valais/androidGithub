package com.example.ezequieldevalais.retrofitexample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ezequieldevalais.retrofitexample.model.Constants;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SelectUserActivity extends ActionBarActivity {

    public static final int REQUEST_USER = 1000;
    private static final String TAG = Constants.TAG;
    private static SelectUserActivity activity;
    private static final String INTENT_SEND_GITHUB_USER= Constants.INTENT_GITHUB_USER;
    @InjectView(R.id.txtGithubUser) TextView  txtGithubUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        activity = this;
        ButterKnife.inject(this);
    }

    @OnClick(R.id.button)
    public void loadRepositoriesView(View v){
        Intent intent = new Intent(activity, GithubUserActivity.class);
        intent.putExtra(INTENT_SEND_GITHUB_USER, txtGithubUser.getText().toString());
        startActivityForResult(intent, REQUEST_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_USER) {
            // Make sure the request was successful

            if (resultCode == RESULT_OK) {
                Log.v(TAG, "result OK");
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                // Do something with the contact here (bigger example below)
            }
            if ( resultCode == RESULT_CANCELED) {
                Log.v(TAG, "User does not exist");
                SnackbarManager.show(
                        Snackbar.with(this)
                                .text("User does not exist"), this);
                //githubUser = null;
            }
        }
    }

}
