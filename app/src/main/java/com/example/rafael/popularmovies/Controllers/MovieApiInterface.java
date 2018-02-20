package com.example.rafael.popularmovies.Controllers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by Rafael on 19/02/2018.
 */

public interface MovieApiInterface {


    @GET("popular?api_key={api}")
    Call<String> getMovieJson(@Path("api") String api);
}
