package com.example.ezequieldevalais.retrofitexample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SelectUserActivity extends ActionBarActivity {

    private SelectUserActivity activity= this;
    public static final String EXTRA_MESSAGE="com.example.ezequieldevalais.retrofitexample.MESSAGE";
    @InjectView(R.id.txtGithubUser) TextView  txtGithubUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.button)
    public void loadRepositoriesView(View v){
        Intent intent = new Intent(activity, GithubUserActivity.class);
        intent.putExtra(EXTRA_MESSAGE, txtGithubUser.getText().toString());
        GithubUserActivity.userName = null;
        startActivity(intent);
    }

}
