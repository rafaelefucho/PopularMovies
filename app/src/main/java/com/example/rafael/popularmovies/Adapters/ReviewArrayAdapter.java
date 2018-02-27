package com.example.rafael.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rafael.popularmovies.R;
import com.example.rafael.popularmovies.Utilities.Review;
import com.example.rafael.popularmovies.detailActivity;

import java.util.List;

/**
 * Created by Rafael on 27/02/2018.
 */

public class ReviewArrayAdapter extends BaseAdapter{

    private Context mContext;
    private List<Review> mReviewList;


    public ReviewArrayAdapter(@NonNull Context context) {
        mContext = context;
    }


    @Override
    public int getCount() {
        if (mReviewList == null) return 0;
        else return mReviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return mReviewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View reviewListItemView = inflater.inflate(R.layout.list_review_item, parent, false);

        TextView author = reviewListItemView.findViewById(R.id.LV_author);
        TextView review = reviewListItemView.findViewById(R.id.LV_review);

        Review currentReview = mReviewList.get(position);

        author.setText(currentReview.getAuthor());
        review.setText(currentReview.getContent());

        return reviewListItemView;
    }

    public void setmReviewList(List<Review> newReviewList){
        mReviewList = newReviewList;
        notifyDataSetChanged();
    }
}
