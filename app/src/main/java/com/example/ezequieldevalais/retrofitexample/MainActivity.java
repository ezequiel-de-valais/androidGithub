package com.example.ezequieldevalais.retrofitexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ezequieldevalais.retrofitexample.model.Gitmodel;
import com.example.ezequieldevalais.retrofitexample.model.Repository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    private RestAdapter restAdapter;
    private MainActivity activity = this;
    private String githubUser = "ezequiel-de-valais";
    //private String githubUser = "dparne";
    private String API = "https://api.github.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        getGithubUser();
        fillRepositryList();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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




    private void getGithubUser() {
        gitapi git = restAdapter.create(gitapi.class);

        git.getFeed(githubUser,new Callback<Gitmodel>() {
            public void success(Gitmodel Gitmodel, Response response) {
                gitHubCalbackSuccess(Gitmodel);
            }
            @Override
            public void failure(RetrofitError error) {
                Log.e("Eze",error.getMessage());
            }
        });
    }

    public void gitHubCalbackSuccess(Gitmodel gitmodel){
        Log.v("Eze","Github id :"+ gitmodel.getId()+
                "\nurl :"+ gitmodel.getUrl()+"\navatar url :"+ gitmodel.getAvatarUrl());

        Log.v("Eze",gitmodel.getAvatarUrl());
        TextView txtName
                = (TextView) findViewById(R.id.txtUserName);
        txtName.setText((CharSequence) gitmodel.getName());
        TextView txtUser = (TextView) findViewById(R.id.txtUser);
        txtUser.setText((CharSequence) gitmodel.getLogin());
        TextView txtGithubId = (TextView) findViewById(R.id.txtGithubId);
        txtGithubId.setText((CharSequence) gitmodel.getId().toString());
        ImageView image = (ImageView) findViewById(R.id.imageGithub);
        //Picasso.with(this).load("https://avatars0.githubusercontent.com/u/5672259?v=3&s=460").into(image);
        Picasso.with(this)
                .load(gitmodel.getAvatarUrl())
                .into(image);

    }

    private void fillRepositryList() {
        gitapi git = restAdapter.create(gitapi.class);
        git.getRepositories(githubUser, new Callback<List<Repository>>() {
            @Override
            public void success(List<Repository> repositories, Response response) {
                GithubRepositoryArrayAdapter chapterListAdapter = new GithubRepositoryArrayAdapter(repositories,activity);
                ListView githubRepositories = (ListView)findViewById(R.id.listView);
                githubRepositories.setAdapter(chapterListAdapter);

                for (Repository repository : repositories) {
                    Log.v("Eze",repository.getFullName());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Eze",error.getMessage());
            }
        });

    }

}