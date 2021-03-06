package com.example.rafael.popularmovies.Utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Movie;

import com.example.rafael.popularmovies.data.MovieContract;

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

    public static ContentValues parseMovieToContentValues(Movies mCurrentMovie) {
        ContentValues resultContentValues = new ContentValues();

        resultContentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mCurrentMovie.getMovie_id());
        resultContentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, mCurrentMovie.getOverview());
        resultContentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, mCurrentMovie.getPoster_path());
        resultContentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, mCurrentMovie.getRelease_date());
        resultContentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, mCurrentMovie.getOriginal_title());
        resultContentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, mCurrentMovie.getVote_average());

        return resultContentValues;
    }

    public static List<Movies> parseFromCursorToMovieList(Cursor dataMovies) {
        //Based on https://stackoverflow.com/questions/10723770/whats-the-best-way-to-iterate-an-android-cursor
        List<Movies> moviesList = new ArrayList<>();

        for (dataMovies.moveToFirst(); !dataMovies.isAfterLast(); dataMovies.moveToNext()) {

            String movieId = dataMovies.getString(dataMovies.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
            String overview = dataMovies.getString(dataMovies.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
            String posterPath = dataMovies.getString(dataMovies.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
            String releaseDate = dataMovies.getString(dataMovies.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
            String title = dataMovies.getString(dataMovies.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
            String voteAverage = dataMovies.getString(dataMovies.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE));

            Movies movie = new Movies(title,posterPath,overview,voteAverage,releaseDate, movieId);
            moviesList.add(movie);
        }

        return moviesList;
    }
}
