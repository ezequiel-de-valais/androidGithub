package com.example.ezequieldevalais.retrofitexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ezequieldevalais.retrofitexample.model.Constants;
import com.example.ezequieldevalais.retrofitexample.model.User;
import com.example.ezequieldevalais.retrofitexample.model.githubAPI;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserCardFragment extends CardFragment {

    private static final String ARG_USERNAME = "username";
    private static final String API = Constants.githubApi;
    private static final String TAG = Constants.TAG;
    //private static final int RESULT_OK = 1 ;
    private static String githubUser = null;
    private RestAdapter restAdapter;

    @InjectView(R.id.txtUserName) TextView txtName;
    @InjectView(R.id.txtUser) TextView txtUser;
    @InjectView(R.id.txtGithubId) TextView txtGithubId;
    @InjectView(R.id.imageGithub) ImageView imageGithub;
    @InjectView(R.id.progress_wheel) ProgressWheel progressWheel;

    public static CardFragment newInstance(String username) {
        CardFragment f = new UserCardFragment();
        Bundle b = new Bundle();
        b.putString(ARG_USERNAME, username);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        githubUser = getArguments().getString(ARG_USERNAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_card,container,false);
        ButterKnife.inject(this, rootView);
        ViewCompat.setElevation(rootView, 50);


        ProgressWheel wheel = new ProgressWheel(getActivity());
        wheel.setBarColor(Color.BLUE);
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        getGithubUser();
        return rootView;
    }

    private void getGithubUser() {
        githubAPI git = restAdapter.create(githubAPI.class);

        git.getUser(githubUser, new Callback<User>() {
            public void success(User Gitmodel, Response response) {
                progressWheel.stopSpinning();
                gitHubCalbackSuccess(Gitmodel);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e(TAG, error.getMessage());
                progressWheel.stopSpinning();
                Activity activity = getActivity();
                Intent output = new Intent();
                activity.setResult(getActivity().RESULT_CANCELED, output);
                activity.finish();

            }
        });
    }

    private void gitHubCalbackSuccess(User gitmodel){
        txtName.setText(gitmodel.getName());
        txtUser.setText(gitmodel.getLogin());
        txtGithubId.setText(gitmodel.getId().toString());
        Log.v(TAG, gitmodel.getAvatarUrl());
        Picasso.with(this.getActivity())
                .load(gitmodel.getAvatarUrl())
                .into(imageGithub);
    }
}