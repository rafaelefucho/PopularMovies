package com.example.rafael.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rafael.popularmovies.R;
import com.example.rafael.popularmovies.Utilities.Trailer;

import java.util.List;

/**
 * Created by Rafael on 28/02/2018.
 */

public class TrailerAdapterRV extends RecyclerView.Adapter<TrailerAdapterRV.TrailerAdapterRVHolder>{


    private Context mContext;
    private List<Trailer> mTrailerList;
    private TrailerItemClickListener mTrailerItemClickListener;

    public interface TrailerItemClickListener {
        void onTrailerItemClick (Trailer clickedTrailer);
    }

    public TrailerAdapterRV(Context newContext){

        mContext = newContext;
        mTrailerItemClickListener = (TrailerItemClickListener) newContext;
    }



    public void setReviewList(List<Trailer> trailerListNew) {
        mTrailerList = trailerListNew;
        notifyDataSetChanged();
    }

    @Override
    public TrailerAdapterRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_trailer_item, parent, false);
        return new TrailerAdapterRVHolder(view);

    }

    @Override
    public void onBindViewHolder(TrailerAdapterRVHolder holder, int position) {
        Trailer trailer = mTrailerList.get(position);

        holder.mName.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        if (mTrailerList == null) return 0;
        else return mTrailerList.size();
    }

    public class TrailerAdapterRVHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView mName;

        public TrailerAdapterRVHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.LV_name);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Trailer clickedTrailer = mTrailerList.get(clickedPosition);
            if(clickedTrailer == null){
                return;
            }

            mTrailerItemClickListener.onTrailerItemClick(clickedTrailer);
        }
    }
}
