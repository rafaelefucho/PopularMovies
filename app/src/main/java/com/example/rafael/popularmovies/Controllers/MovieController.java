package com.example.rafael.popularmovies.Controllers;

import com.example.rafael.popularmovies.MainActivity;
import com.example.rafael.popularmovies.Utilities.load;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Rafael on 19/02/2018.
 */

public class MovieController {

    private static Retrofit mRetrofit = null;

    public static Retrofit getClient(MainActivity mainActivity){

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        String url = "http://api.themoviedb.org/3/movie/";
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        return mRetrofit;

    }
}
