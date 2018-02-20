package com.example.rafael.popularmovies.Controllers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by Rafael on 19/02/2018.
 */

public interface MovieApiInterface {


    @GET("popular?")
        Call<String> getMovieJson(@Query("api_key") String api);
}
