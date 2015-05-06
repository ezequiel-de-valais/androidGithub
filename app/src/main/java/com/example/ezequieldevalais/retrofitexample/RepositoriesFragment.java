package com.example.ezequieldevalais.retrofitexample;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ezequieldevalais.retrofitexample.model.Constants;
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

    private static final String ARG_USERNAME = "username";
    private static final String TAG = Constants.TAG ;
    private static final String API = Constants.githubApi;
    private RestAdapter restAdapter;
    private String userName ;

    @InjectView(R.id.txtUserName) TextView txtUserName;
    @InjectView(R.id.listRepositories) ListView githubRepositories;

    public static CardFragment newInstance(String username) {
        CardFragment f = new RepositoriesFragment();
        Bundle b = new Bundle();
        b.putString(ARG_USERNAME, username);

        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getArguments().getString(ARG_USERNAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_repositories_card,container,false);
        ButterKnife.inject(this, rootView);
        ViewCompat.setElevation(rootView, 50);
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