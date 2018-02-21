package com.example.rafael.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rafael.popularmovies.Adapters.MovieAdapterRV;
import com.example.rafael.popularmovies.Controllers.MovieApiInterface;
import com.example.rafael.popularmovies.Controllers.MovieController;
import com.example.rafael.popularmovies.Utilities.Movies;
import com.example.rafael.popularmovies.Utilities.NetworkUtils;
import com.example.rafael.popularmovies.Utilities.Parsing;
import com.example.rafael.popularmovies.Utilities.load;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private List<Movies> mMoviesList;

    private RecyclerView mMoviesRV;
    private MovieAdapterRV mMovieAdapterRV;

    final private String MOST_POPULAR = "popular";
    final private String TOP_RATED = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();
        loadDatafromMoviedb(MOST_POPULAR);


    }

    private void setupRecyclerView() {
        mMoviesRV = findViewById(R.id.recyclerview_movies);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        mMoviesRV.setLayoutManager(layoutManager);
        mMoviesRV.setHasFixedSize(true);
        mMovieAdapterRV = new MovieAdapterRV(MainActivity.this);
        mMoviesRV.setAdapter(mMovieAdapterRV);
    }

    private void loadDatafromMoviedb(String sortBy) {

        if (!NetworkUtils.isInternetConnectionAvailable(MainActivity.this)) {
            //Need to add another screen for connection failure
            return;
        }

        //TODO to do all the comentaires
        //To add a switch to get popularity or top rated
        // Also to hide the apiKey
        //String url = "http://api.themoviedb.org/3/movie/popular?api_key=" + "1f603ccb7cbe37247347e5a8fbaae643";

        MovieApiInterface apiInterface = MovieController
                .getClient(this)
                .create(MovieApiInterface.class);

        String url = sortBy + "?api_key=" + load.loadApiKey(this);
        final retrofit2.Call<String> responseCall = apiInterface.getMovieJson(url);

        responseCall.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                if (response.isSuccessful()) {

                    String jsonMovieData = response.body();

                    mMoviesList = Parsing.parseFromJsonMovies(jsonMovieData);
                    mMovieAdapterRV.setMovieList(mMoviesList);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {

            }
        });

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

                switch (position){
                    case 0:
                        //Most Popular
                        loadDatafromMoviedb(MOST_POPULAR);
                        break;
                    case 1:
                        //Top rated
                        loadDatafromMoviedb(TOP_RATED);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        return true;
    }


}
