package com.example.ezequieldevalais.retrofitexample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ezequieldevalais.retrofitexample.model.Repository;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RepositoriesActivity extends ActionBarActivity {

    private static final String TAG = "Eze" ;
    private static final String API = "https://api.github.com";
    private RestAdapter restAdapter;
    private String userName;
    ActionBarActivity activity= this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        Intent intent = getIntent();
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();

        userName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.v(TAG,"in repository activity, message:" + userName);
        TextView txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserName.setText(userName);
        fillRepositryList();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_repositories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fillRepositryList() {
        gitapi git = restAdapter.create(gitapi.class);
        git.getRepositories(userName, new Callback<List<Repository>>() {

            @Override
            public void success(List<Repository> repositories, Response response) {
                GithubRepositoryArrayAdapter chapterListAdapter = new GithubRepositoryArrayAdapter(repositories,activity);
                ListView githubRepositories = (ListView)findViewById(R.id.listRepositories);
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

}
