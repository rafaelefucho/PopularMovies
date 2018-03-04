package com.example.rafael.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafael.popularmovies.Adapters.ReviewRVAdapter;
import com.example.rafael.popularmovies.Adapters.TrailerAdapterRV;
import com.example.rafael.popularmovies.Controllers.MovieApiInterface;
import com.example.rafael.popularmovies.Controllers.MovieController;
import com.example.rafael.popularmovies.Utilities.Load;
import com.example.rafael.popularmovies.Utilities.Movies;
import com.example.rafael.popularmovies.Utilities.Parsing;
import com.example.rafael.popularmovies.Utilities.Review;
import com.example.rafael.popularmovies.Utilities.Trailer;
import com.example.rafael.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

import java.util.List;

public class detailActivity extends AppCompatActivity implements TrailerAdapterRV.TrailerItemClickListener{

    private static final String REVIEW = "reviews";
    private static final String VIDEOS = "videos";
    private Movies mCurrentMovie;

    private ImageView mMoviePoster;
    private TextView mTitle;
    private TextView mVoteAverage;
    private TextView mReleaseDate;
    private TextView mOverview;

    private ImageButton mFavoriteImageButton;

    private List<Review> mReviewList;
    private List<Trailer> mTrailerList;

    private RecyclerView mReviewRV;
    private RecyclerView mTrailerRV;

    private ReviewRVAdapter mReviewRVAdapter;
    private TrailerAdapterRV mTrailerAdapterRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        mCurrentMovie = (Movies) intent.getParcelableExtra("currentMovie");


        setupDetailView();
        setupReviewRecyclerView();
        setupTrailerRecyclerView();
        setupFavoriteButton();
        loadMovieInfo();


    }

    private void setupFavoriteButton() {
        mFavoriteImageButton = findViewById(R.id.favorite_button);

        if(movieExistInFavorites()){
            mFavoriteImageButton.setImageResource(R.drawable.star_pressed);
        }
        else{
            mFavoriteImageButton.setImageResource(R.drawable.star_unpressed);
        }


    }

    private void setupTrailerRecyclerView() {
        mTrailerRV = findViewById(R.id.trailer_RV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,
                false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                2, GridLayoutManager.HORIZONTAL, false);
        mTrailerRV.setLayoutManager(gridLayoutManager);
        mTrailerAdapterRV = new TrailerAdapterRV(this);
        mTrailerRV.setAdapter(mTrailerAdapterRV);
        loadList(VIDEOS);

    }

    private void setupReviewRecyclerView() {
        mReviewRV = findViewById(R.id.review_RV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,
                false);
        mReviewRV.setLayoutManager(layoutManager);
        mReviewRVAdapter = new ReviewRVAdapter(this);
        mReviewRV.setAdapter(mReviewRVAdapter);
        loadList(REVIEW);
    }

    private void loadList(final String urlParameter) {

        String urlReviews = mCurrentMovie.getMovie_id() + "/" + urlParameter + "?api_key=" + Load.loadApiKey(this);

        MovieApiInterface apiInterface = MovieController
                .getClient()
                .create(MovieApiInterface.class);

        final retrofit2.Call<String> responseCall = apiInterface.getMovieJson(urlReviews);

        responseCall.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful()) {

                    String jsonData = response.body();

                    switch (urlParameter){
                        case REVIEW:
                            mReviewList = Parsing.parseFromJsonReviews(jsonData);
                            mReviewRVAdapter.setReviewList(mReviewList);
                            break;
                        case VIDEOS:
                            mTrailerList = Parsing.parseFromJsonTrailers(jsonData);
                            mTrailerAdapterRV.setReviewList(mTrailerList);
                    }

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

    @Override
    public void onTrailerItemClick(Trailer clickedTrailer) {
        // Based on https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent

        String id = clickedTrailer.getKey();

        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    public void shareIntent(View view) {


        getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,null,null);


        if (mTrailerList.isEmpty()){
            Toast.makeText(this, "Tough luck no Trailers for this inspiring Movie",
                    Toast.LENGTH_SHORT).show();
            return;
        }


        Trailer trailer = mTrailerList.get(0);
        String id = trailer.getKey();

        // Based on https://stackoverflow.com/questions/38322233/how-to-share-a-text-link-via-text-intent
        String textToShare = "I want to share this awesome trailer with you " +
                "http://www.youtube.com/watch?v="+ id + " " +
                "of the movie " + mCurrentMovie.getOriginal_title();

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share with your friends");
        intent.putExtra(Intent.EXTRA_TEXT, textToShare);

        startActivity(Intent.createChooser(intent, "Share the awesomeness"));



    }

    public void updateToFavorite(View view) {

        if (!movieExistInFavorites()) {
            //Add Current movie to Favorites

            Uri uriInsert = MovieContract.MovieEntry.CONTENT_URI;
            ContentValues cv = Parsing.parseMovieToContentValues(mCurrentMovie);

            getContentResolver().insert(uriInsert, cv);
            mFavoriteImageButton.setImageResource(R.drawable.star_pressed);

            Toast.makeText(this, R.string.add_to_favorites,
                    Toast.LENGTH_SHORT).show();
        }
        else {
            //Delete current movie from favorites

            String movieId = mCurrentMovie.getMovie_id();
            Uri uriDelete = MovieContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(movieId).build();

            getContentResolver().delete(uriDelete,null,null);
            mFavoriteImageButton.setImageResource(R.drawable.star_unpressed);
            Toast.makeText(this, R.string.remove_from_favorites,
                    Toast.LENGTH_SHORT).show();

        }
    }

    public boolean movieExistInFavorites(){
        String movieId = mCurrentMovie.getMovie_id();
        Uri uri = MovieContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(movieId).build();

        Cursor resultCursor = getContentResolver().query(uri,null,null,null,null);

        return (resultCursor.getCount() != 0);
    }
}
