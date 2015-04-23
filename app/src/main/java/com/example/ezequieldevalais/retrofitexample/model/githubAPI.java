package com.example.ezequieldevalais.retrofitexample.model;

/**
 * Created by EzequielDeValais on 4/16/15.
 */

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface githubAPI {
    @GET("/users/{user}")
    public void getUser(
            @Path("user") String user,
            Callback<User> response
    );

    @GET("/users/{user}/repos")
    public void getRepositories(
            @Path("user") String user,
            Callback<List<Repository>> response
    );


}

