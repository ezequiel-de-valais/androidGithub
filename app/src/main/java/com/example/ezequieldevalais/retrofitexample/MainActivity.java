package com.example.ezequieldevalais.retrofitexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("Eze", "start");

        callGithub();

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




    private void callGithub() {

        Log.v("Eze","seteando adapter");
        String API = "https://api.github.com";
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        Log.v("Eze","seteado restAdapter");

        gitapi git = restAdapter.create(gitapi.class);
        String user = "ezequiel-de-valais";
        user ="basil2style";
        Log.v("Eze","esperando respuesta");
        git.getFeed(user,new Callback<Gitmodel>() {
            public void success(Gitmodel Gitmodel, Response response) {
                Log.v("Eze","Github id :"+ Gitmodel.getId()+
                        "\nurl :"+ Gitmodel.getUrl()+"\navatar url :"+ Gitmodel.getAvatarUrl());
                //pbar.setVisibility(View.INVISIBLE); //disable progressbar
            }
            @Override
            public void failure(RetrofitError error) {
                Log.v("Eze",error.getMessage());
                //pbar.setVisibility(View.INVISIBLE); //disable progressbar
            }
        });



    }


}