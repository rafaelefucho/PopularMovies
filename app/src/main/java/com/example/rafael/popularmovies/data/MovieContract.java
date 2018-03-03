package com.example.rafael.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Rafael on 02/03/2018.
 */

public class MovieContract {

    public static final String AUTHORITY = "com.example.rafael.popularmovies";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    public static final String PATH_MOVIES = "movies";


    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "favorite_movies";


        public static final  String COLUMN_TITLE = "original_title";
        public static final  String COLUMN_POSTER_PATH = "poster_path";
        public static final  String COLUMN_OVERVIEW = "overview";
        public static final  String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final  String COLUMN_RELEASE_DATE = "release_date";
        public static final  String COLUMN_MOVIE_ID = "movie_id";

    }
}
