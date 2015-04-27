package com.example.ezequieldevalais.retrofitexample;
/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ezequieldevalais.retrofitexample.model.Repository;
import com.example.ezequieldevalais.retrofitexample.model.githubAPI;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RepositoriesFragment extends CardFragment {

    private static final String ARG_POSITION = "position";
    private static final String ARG_USERNAME = "username";

    private static String name;
    private static final String TAG = "Eze" ;
    private static final String API = "https://api.github.com";
    private RestAdapter restAdapter;
    private String userName ;
//    private Intent intent;
    @InjectView(R.id.txtUserName) TextView txtUserName;
    @InjectView(R.id.listRepositories) ListView githubRepositories;

    public static String githubUser = null;



    private int position;
//    private RestAdapter restAdapter;


    public static CardFragment newInstance(int position, String username) {
        CardFragment f = new RepositoriesFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putString(ARG_USERNAME, username);

        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        userName = getArguments().getString(ARG_USERNAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_repositories_card,container,false);
        ButterKnife.inject(this, rootView);
        ViewCompat.setElevation(rootView, 50);
//        userName = "ezequiel-de-valais";
//        userName = "dpare";
        txtUserName.setText(userName);
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        fillRepositryList();
        return rootView;
    }



    private void fillRepositryList() {
        githubAPI git = restAdapter.create(githubAPI.class);
        git.getRepositories(userName,new RepositoriesCallback());
    }

    private class RepositoriesCallback implements Callback<List<Repository>> {

        @Override
        public void success(List<Repository> repositories, Response response) {
            Activity activity= getActivity();

            GithubRepositoryArrayAdapter chapterListAdapter = new GithubRepositoryArrayAdapter(repositories,activity);
            githubRepositories = (ListView)activity.findViewById(R.id.listRepositories);
//            progressWheel.stopSpinning();
            githubRepositories.setAdapter(chapterListAdapter);

            for (Repository repository : repositories) {
                Log.v(TAG,repository.getFullName());
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(TAG,error.getMessage());
        }
    }
}