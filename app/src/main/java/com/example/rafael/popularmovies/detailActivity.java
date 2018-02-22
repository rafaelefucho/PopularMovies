package com.example.rafael.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafael.popularmovies.Utilities.Movies;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class detailActivity extends AppCompatActivity {

    private Movies mCurrentMovie;

    private ImageView mMoviePoster;
    private TextView mTitle;
    private TextView mVoteAverage;
    private TextView mReleaseDate;
    private TextView mOverview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        mCurrentMovie = (Movies) intent.getParcelableExtra("currentMovie");


        setTitle(mCurrentMovie.getOriginal_title());
        setupDetailView();

    }

    private void setupDetailView() {
        mMoviePoster = findViewById(R.id.movie_poster_image_DV);
        mTitle = findViewById(R.id.movie_title_DV);
        mVoteAverage = findViewById(R.id.vote_average_DV);
        mReleaseDate = findViewById(R.id.release_date_DV);
        mOverview  = findViewById(R.id.overview_DV);

        mTitle.setText(mCurrentMovie.getOriginal_title());
        mVoteAverage.setText(mCurrentMovie.getVote_average());
        mReleaseDate.setText(mCurrentMovie.getRelease_date());
        mOverview.setText(mCurrentMovie.getOverview());

        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w185/" + mCurrentMovie.getPoster_path())
                .into(mMoviePoster);
    }
}
