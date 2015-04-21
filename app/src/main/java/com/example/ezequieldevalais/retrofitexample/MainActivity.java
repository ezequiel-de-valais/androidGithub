package com.example.ezequieldevalais.retrofitexample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ezequieldevalais.retrofitexample.model.Gitmodel;
import com.squareup.picasso.Picasso;


import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    public static final String EXTRA_MESSAGE = "com.example.ezequieldevalais.retrofitexample.MESSAGE" ;
    private RestAdapter restAdapter;
    private MainActivity activity = this;
    private String githubUser = "ezequiel-de-valais";
    //private String githubUser = "dparne";
    private String API = "https://api.github.com";
    private String TAG = "Eze";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        getGithubUser();
        setRepoButton();
    }

    private void setRepoButton() {
        Button buttonRepos = (Button) this.findViewById(R.id.buttonRepos);
        buttonRepos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, RepositoriesActivity.class);
                intent.putExtra(EXTRA_MESSAGE, githubUser);
                startActivity(intent);

            }
        });
    }

    private void getGithubUser() {
        gitapi git = restAdapter.create(gitapi.class);

        git.getFeed(githubUser,new Callback<Gitmodel>() {
            public void success(Gitmodel Gitmodel, Response response) {
                gitHubCalbackSuccess(Gitmodel);
            }
            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG,error.getMessage());
            }
        });
    }

    public void gitHubCalbackSuccess(Gitmodel gitmodel){
        Log.v(TAG, gitmodel.getId()+ "\n"+ gitmodel.getUrl()+ "\n" + gitmodel.getAvatarUrl());

        Log.v(TAG,gitmodel.getAvatarUrl());
        TextView txtName
                = (TextView) findViewById(R.id.txtUserName);
        txtName.setText((CharSequence) gitmodel.getName());

        TextView txtUser = (TextView) findViewById(R.id.txtUser);
        txtUser.setText((CharSequence) gitmodel.getLogin());
        TextView txtGithubId = (TextView) findViewById(R.id.txtGithubId);
        txtGithubId.setText((CharSequence) gitmodel.getId().toString());
        ImageView image = (ImageView) findViewById(R.id.imageGithub);
        Picasso.with(this)
                .load(gitmodel.getAvatarUrl())
                .into(image);

    }



}