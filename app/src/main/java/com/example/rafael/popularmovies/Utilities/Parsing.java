package com.example.rafael.popularmovies.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 17/02/2018.
 */

public class Parsing {

    public static List<Movies> parseFromJsonMovies(String jsonMovieData) {

        try {
            List<Movies> moviesList = new ArrayList<>();
            JSONObject jsonObjectMovieData = new JSONObject(jsonMovieData);
            JSONArray jsonArrayMovies = jsonObjectMovieData.getJSONArray("results");

            for(int i = 0; i < jsonArrayMovies.length(); i++){
                JSONObject movieIterator = jsonArrayMovies.getJSONObject(i);

                String original_title = movieIterator.getString("original_title");
                String poster_path = movieIterator.getString("poster_path");
                String overview = movieIterator.getString("overview");
                String vote_average = movieIterator.getString("vote_average");
                String release_date = movieIterator.getString("release_date");

                Movies movie = new Movies(original_title,poster_path,overview,vote_average,release_date);

                moviesList.add(movie);
            }
            return moviesList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
