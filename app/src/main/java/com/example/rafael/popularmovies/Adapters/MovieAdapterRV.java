package com.example.rafael.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rafael.popularmovies.R;
import com.example.rafael.popularmovies.Utilities.Movies;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by Rafael on 17/02/2018.
 */

public class MovieAdapterRV extends RecyclerView.Adapter<MovieAdapterRV.MovieAdapterRVViewHolder> {

    private List<Movies> mMoviesList;
    private Context mContext;

    public MovieAdapterRV(Context mContext) {
        this.mMoviesList = mMoviesList;
        this.mContext = mContext;
    }

    @Override
    public MovieAdapterRVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieAdapterRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterRVViewHolder holder, int position) {

        Movies movie = mMoviesList.get(position);
        String urlPosterPath = "http://image.tmdb.org/t/p/w185/" + movie.getPoster_path();

        Picasso.with(mContext)
                .load(urlPosterPath)
                .into(holder.mImageViewPoster);
    }

    @Override
    public int getItemCount() {
        if(mMoviesList == null) {
            return 0;
        }
        return mMoviesList.size();
    }

    public void setMovieList(List<Movies> mMoviesListNew) {
        mMoviesList = mMoviesListNew;
        notifyDataSetChanged();
    }

    public class MovieAdapterRVViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView mImageViewPoster;

        public MovieAdapterRVViewHolder(View itemView) {
            super(itemView);
            mImageViewPoster = itemView.findViewById(R.id.movie_poster_image);
            //Soon to add the onClickListener to the View
        }

        @Override
        public void onClick(View view) {

        }
    }
}
