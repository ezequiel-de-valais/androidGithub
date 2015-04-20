package com.example.ezequieldevalais.retrofitexample;

/**
 * Created by EzequielDeValais on 4/16/15.
 */

import com.example.ezequieldevalais.retrofitexample.model.Gitmodel;
import com.example.ezequieldevalais.retrofitexample.model.Repository;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface gitapi {
    @GET("/users/{user}")      //here is the other url part.best way is to start using /
    public void getFeed(@Path("user") String user, Callback<Gitmodel> response);     //string user is for passing values from edittext for eg: user=basil2style,google
    //response is the response from the server which is now in the POJO

    @GET("/users/{user}/repos")      //here is the other url part.best way is to start using /
    public void getRepositories(@Path("user") String user, Callback<List<Repository>> response);     //string user is for passing values from edittext for eg: user=basil2style,google


}

