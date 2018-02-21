package com.example.rafael.popularmovies.Controllers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by Rafael on 19/02/2018.
 */

public interface MovieApiInterface {


    @GET
        Call<String> getMovieJson(@Url String api);
}
