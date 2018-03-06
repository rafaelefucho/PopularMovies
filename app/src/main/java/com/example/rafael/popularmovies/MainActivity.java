package com.example.rafael.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rafael.popularmovies.Adapters.MovieAdapterRV;
import com.example.rafael.popularmovies.Controllers.MovieApiInterface;
import com.example.rafael.popularmovies.Controllers.MovieController;
import com.example.rafael.popularmovies.Utilities.Load;
import com.example.rafael.popularmovies.Utilities.Movies;
import com.example.rafael.popularmovies.Utilities.NetworkUtils;
import com.example.rafael.popularmovies.Utilities.Parsing;
import com.example.rafael.popularmovies.data.MovieContract;

import java.util.List;


public class MainActivity extends AppCompatActivity implements
        MovieAdapterRV.MoviesItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int TASK_LOADER_ID = 0;
    private static final String SAVED_LAYOUT_MANAGER = "recyclerView.layout" ;

    private int mFirstVisiblePositionRecyclerView;

    private List<Movies> mMoviesList;

    private RecyclerView mMoviesRV;
    private MovieAdapterRV mMovieAdapterRV;

    private TextView mNoInternetTV;

    static final private String MOST_POPULAR = "popular";
    static final private String TOP_RATED = "top_rated";
    static final private String FAVORITES = "favorites";

    private String mCurrentOrderBy;

    private SharedPreferences mSharedPreferences;
    static final private String PREFERENCES = "preferences";
    static final private String ORDER_BY = "orderBy";

    private boolean mUserIsInteracting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String sortBy = mSharedPreferences.getString(ORDER_BY, null);
        if(sortBy != null){
            mCurrentOrderBy = sortBy;
        }
        else{
            mCurrentOrderBy = MOST_POPULAR;
        }

        setupRecyclerView();
        loadDatafromMoviedb(mCurrentOrderBy);

    }



    private void setupRecyclerView() {
        mMoviesRV = findViewById(R.id.recyclerview_movies);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        mMoviesRV.setLayoutManager(layoutManager);
        mMoviesRV.setHasFixedSize(true);
        mMovieAdapterRV = new MovieAdapterRV(MainActivity.this);
        mMoviesRV.setAdapter(mMovieAdapterRV);

        mNoInternetTV = findViewById(R.id.no_internet);
    }

    private void loadDatafromMoviedb(String sortBy) {

        switch (sortBy){
            case FAVORITES:
                getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null,MainActivity.this);
                setTitle("Favorites");
                return;

            case MOST_POPULAR:
                setTitle("Most Popular");
                break;

            case TOP_RATED:
                setTitle("Top Rated");
                break;
        }

        if (!NetworkUtils.isInternetConnectionAvailable(MainActivity.this)) {
            mMoviesRV.setVisibility(View.GONE);
            mNoInternetTV.setVisibility(View.VISIBLE);
            return;
        }
        else {
            mMoviesRV.setVisibility(View.VISIBLE);
            mNoInternetTV.setVisibility(View.GONE);
        }



        //String url = "http://api.themoviedb.org/3/movie/popular?api_key=" + " your API";

        MovieApiInterface apiInterface = MovieController
                .getClient()
                .create(MovieApiInterface.class);

        String url = sortBy + "?api_key=" + Load.loadApiKey(this);
        final retrofit2.Call<String> responseCall = apiInterface.getMovieJson(url);

        responseCall.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful()) {

                    String jsonMovieData = response.body();

                    mMoviesList = Parsing.parseFromJsonMovies(jsonMovieData);
                    mMovieAdapterRV.setMovieList(mMoviesList);
                    restoreLayoutManagerPosition();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {

            }
        });

    }

    private void restoreLayoutManagerPosition() {

        mMoviesRV.getLayoutManager().scrollToPosition(mFirstVisiblePositionRecyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Based on https://stackoverflow.com/questions/37250397/how-to-add-a-spinner-next-to-a-menu-in-the-toolbar
        getMenuInflater().inflate(R.menu.menu_with_spinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (!mUserIsInteracting) return;
                switch (position){
                    case 0:
                        //Most Popular
                        loadDatafromMoviedb(MOST_POPULAR);
                        mCurrentOrderBy = MOST_POPULAR;
                        break;
                    case 1:
                        //Top rated
                        loadDatafromMoviedb(TOP_RATED);
                        mCurrentOrderBy = TOP_RATED;
                        break;
                    case 2:
                        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null,MainActivity.this);
                        mCurrentOrderBy = FAVORITES;
                        break;
                }

                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(ORDER_BY,mCurrentOrderBy);
                editor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        return true;
    }

    @Override
    public void onMovieItemClick(Movies clickedMovie) {
        Intent detailActivity = new Intent(this, DetailActivity.class);
        detailActivity.putExtra("currentMovie", clickedMovie);
        startActivity(detailActivity);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mTaskData = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(mTaskData != null){
                    deliverResult(mTaskData);
                }
                else{
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor dataMovies) {
        mMoviesList = Parsing.parseFromCursorToMovieList(dataMovies);
        mMovieAdapterRV.setMovieList(mMoviesList);
        restoreLayoutManagerPosition();

        mMoviesRV.setVisibility(View.VISIBLE);
        mNoInternetTV.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        mUserIsInteracting = true;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int firstVisiblePosition = ((GridLayoutManager)mMoviesRV.getLayoutManager()).findFirstVisibleItemPosition();
        outState.putInt("firstVisiblePosition", firstVisiblePosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mFirstVisiblePositionRecyclerView = savedInstanceState.getInt("firstVisiblePosition");
    }
}
