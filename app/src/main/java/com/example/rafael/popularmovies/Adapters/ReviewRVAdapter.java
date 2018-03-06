package com.example.rafael.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rafael.popularmovies.DetailActivity;
import com.example.rafael.popularmovies.R;
import com.example.rafael.popularmovies.Utilities.Review;

import java.util.List;

/**
 * Created by Rafael on 28/02/2018.
 */

public class ReviewRVAdapter extends RecyclerView.Adapter<ReviewRVAdapter.ReviewRVAdapterViewHolder>{

    private Context mContext;
    private List<Review> mReviewList;

    public ReviewRVAdapter(DetailActivity detailActivity) {
        mContext = detailActivity;
    }

    @Override
    public ReviewRVAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_review_item, parent, false);
        return new ReviewRVAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ReviewRVAdapterViewHolder holder, int position) {
        Review review = mReviewList.get(position);

        holder.mAuthor.setText(review.getAuthor());
        holder.mReview.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        if (mReviewList == null) return 0;
        else return mReviewList.size();
    }

    public void setReviewList(List<Review> mReviewListNew) {
        mReviewList = mReviewListNew;
        notifyDataSetChanged();
    }

    public class ReviewRVAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView mAuthor;
        private final TextView mReview;

        public ReviewRVAdapterViewHolder(View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.LV_author);
            mReview = itemView.findViewById(R.id.LV_review);
        }
    }
}
