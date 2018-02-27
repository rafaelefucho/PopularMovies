package com.example.rafael.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rafael.popularmovies.Adapters.ReviewArrayAdapter;
import com.example.rafael.popularmovies.Controllers.MovieApiInterface;
import com.example.rafael.popularmovies.Controllers.MovieController;
import com.example.rafael.popularmovies.Utilities.Load;
import com.example.rafael.popularmovies.Utilities.Movies;
import com.example.rafael.popularmovies.Utilities.Parsing;
import com.example.rafael.popularmovies.Utilities.Review;
import com.example.rafael.popularmovies.Utilities.Trailer;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class detailActivity extends AppCompatActivity {

    private Movies mCurrentMovie;

    private ImageView mMoviePoster;
    private TextView mTitle;
    private TextView mVoteAverage;
    private TextView mReleaseDate;
    private TextView mOverview;

    private List<Review> mReviewList;
    private List<Trailer> mTrailerList;

    private ListView mReviewLV;
    private ListView mTrailerLV;

    private ReviewArrayAdapter mReviewArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        mCurrentMovie = (Movies) intent.getParcelableExtra("currentMovie");


        setupDetailView();
        setupReviewListView();
        loadMovieInfo();
        loadReviews();
    }

    private void setupReviewListView() {
        mReviewLV = findViewById(R.id.review_LV);
        mReviewArrayAdapter = new ReviewArrayAdapter(this);
        mReviewLV.setAdapter(mReviewArrayAdapter);

    }

    private void loadReviews() {

        String urlReviews = mCurrentMovie.getMovie_id() + "/reviews?api_key=" + Load.loadApiKey(this);

        MovieApiInterface apiInterface = MovieController
                .getClient()
                .create(MovieApiInterface.class);

        final retrofit2.Call<String> responseCall = apiInterface.getMovieJson(urlReviews);

        responseCall.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful()) {

                    String jsonMovieData = response.body();

                    mReviewList = Parsing.parseFromJsonReviews(jsonMovieData);
                    mReviewArrayAdapter.setmReviewList(mReviewList);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {

            }
        });


    }

    private void loadMovieInfo() {

        setTitle(mCurrentMovie.getOriginal_title());
        mTitle.setText(mCurrentMovie.getOriginal_title());
        mVoteAverage.setText(mCurrentMovie.getVote_average());
        mReleaseDate.setText(mCurrentMovie.getRelease_date());
        mOverview.setText(mCurrentMovie.getOverview());

        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w185/" + mCurrentMovie.getPoster_path())
                .into(mMoviePoster);
    }

    private void setupDetailView() {
        mMoviePoster = findViewById(R.id.movie_poster_image_DV);
        mTitle = findViewById(R.id.movie_title_DV);
        mVoteAverage = findViewById(R.id.vote_average_DV);
        mReleaseDate = findViewById(R.id.release_date_DV);
        mOverview  = findViewById(R.id.overview_DV);

    }
}
