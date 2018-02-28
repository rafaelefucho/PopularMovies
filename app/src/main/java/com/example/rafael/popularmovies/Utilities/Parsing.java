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
                String id = movieIterator.getString("id");

                Movies movie = new Movies(original_title,poster_path,overview,vote_average,release_date, id);

                moviesList.add(movie);
            }
            return moviesList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Review> parseFromJsonReviews(String jsonReviewData) {

        try {
            List<Review> reviewsList = new ArrayList<>();
            JSONObject jsonObjectReviewData = new JSONObject(jsonReviewData);
            JSONArray jsonArrayReviews = jsonObjectReviewData.getJSONArray("results");

            for(int i = 0; i < jsonArrayReviews.length(); i++){
                JSONObject movieIterator = jsonArrayReviews.getJSONObject(i);

                String author = movieIterator.getString("author");
                String content = movieIterator.getString("content");

                Review review = new Review(author,content);

                reviewsList.add(review);
            }

            return reviewsList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static List<Trailer> parseFromJsonTrailers(String jsonTrailerData) {

        try {
            List<Trailer> trailersList = new ArrayList<>();
            JSONObject jsonObjectTrailerData = new JSONObject(jsonTrailerData);
            JSONArray jsonArrayTrailers = jsonObjectTrailerData.getJSONArray("results");

            for(int i = 0; i < jsonArrayTrailers.length(); i++){
                JSONObject movieIterator = jsonArrayTrailers.getJSONObject(i);

                String key = movieIterator.getString("key");
                String name = movieIterator.getString("name");

                Trailer trailer = new Trailer(key,name);


                trailersList.add(trailer);
            }

            return trailersList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;


    }
}
