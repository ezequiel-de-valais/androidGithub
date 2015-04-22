package com.example.ezequieldevalais.retrofitexample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ezequieldevalais.retrofitexample.model.Repository;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RepositoriesActivity extends ActionBarActivity {

    private static final String TAG = "Eze" ;
    private static final String API = "https://api.github.com";
    private RestAdapter restAdapter;
    private String userName ;
    ActionBarActivity activity= this;
    private Intent intent;
    @InjectView(R.id.txtUserName) TextView txtUserName;
    @InjectView(R.id.progress_wheel) ProgressWheel progressWheel;
    @InjectView(R.id.listRepositories) ListView githubRepositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        intent = getIntent();
        ButterKnife.inject(this);

        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();

        setName();
        fillRepositryList();

    }


    private void fillRepositryList() {
        githubAPI git = restAdapter.create(githubAPI.class);
        git.getRepositories(userName, new Callback<List<Repository>>() {

            @Override
            public void success(List<Repository> repositories, Response response) {
                GithubRepositoryArrayAdapter chapterListAdapter = new GithubRepositoryArrayAdapter(repositories,activity);
                githubRepositories = (ListView)findViewById(R.id.listRepositories);
                progressWheel.stopSpinning();
                githubRepositories.setAdapter(chapterListAdapter);

                for (Repository repository : repositories) {
                    Log.v(TAG,repository.getFullName());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG,error.getMessage());
            }
        });
    }

    public void setName(){
        userName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.v(TAG,"in repository activity, message:" + userName);
        txtUserName.setText(userName);
    }

}
