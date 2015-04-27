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


        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.view.ViewCompat;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.ezequieldevalais.retrofitexample.model.User;
        import com.example.ezequieldevalais.retrofitexample.model.githubAPI;
        import com.squareup.picasso.Picasso;

        import butterknife.ButterKnife;
        import butterknife.InjectView;
        import retrofit.Callback;
        import retrofit.RestAdapter;
        import retrofit.RetrofitError;
        import retrofit.client.Response;

public class UserCardFragment extends CardFragment {

    private static final String ARG_POSITION = "position";
    private static final String ARG_USERNAME = "username";
    private static String name;

    @InjectView(R.id.txtUserName) TextView txtName;
    @InjectView(R.id.txtUser) TextView txtUser;
    @InjectView(R.id.txtGithubId) TextView txtGithubId;
    @InjectView(R.id.imageGithub) ImageView imageGithub;

    public static String githubUser = null;

    private String API = "https://api.github.com";
    private String TAG = "Eze";


    private int position;
    private RestAdapter restAdapter;
    private String userName;


    public static CardFragment newInstance(int position, String username) {
        //name = "Ezequiel";
        CardFragment f = new UserCardFragment();
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
        githubUser = getArguments().getString(ARG_USERNAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_card,container,false);
        ButterKnife.inject(this, rootView);
        ViewCompat.setElevation(rootView, 50);
        txtName.setText("CARD anotherSuperAwesomeCard "+position+ "  -  " + name);
        //githubUser = "ezequiel-de-valais";

        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        getGithubUser();
        return rootView;
    }


    private void getGithubUser() {
        githubAPI git = restAdapter.create(githubAPI.class);

        git.getUser(githubUser, new Callback<User>() {
            public void success(User Gitmodel, Response response) {
//                progressWheel.stopSpinning();
                gitHubCalbackSuccess(Gitmodel);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e(TAG, error.getMessage());
//                Toast toast = Toast.makeText(activity, "User \"" + githubUser + "\" does not exist" ,Toast.LENGTH_SHORT);
                githubUser = null;
//                progressWheel.stopSpinning();
//                toast.show();
//                finish();
            }
        });
    }

    public void gitHubCalbackSuccess(User gitmodel){
        txtName.setText(gitmodel.getName());
        txtUser.setText(gitmodel.getLogin());
        txtGithubId.setText(gitmodel.getId().toString());
//        buttonRepos.setVisibility(View.VISIBLE);
        Picasso.with(this.getActivity())
                .load(gitmodel.getAvatarUrl())
                .into(imageGithub);
    }
}