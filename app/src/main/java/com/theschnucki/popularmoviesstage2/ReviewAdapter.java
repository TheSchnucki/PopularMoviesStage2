package com.theschnucki.popularmoviesstage2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theschnucki.popularmoviesstage2.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

    public static final String TAG = ReviewAdapter.class.getSimpleName();

    private List<Review> mReviewList;

    public ReviewAdapter() {}

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView authorTv;
        public TextView contentsTv;

        public ReviewAdapterViewHolder (View view) {
            super(view);
            authorTv = view.findViewById(R.id.author_tv);
            contentsTv = view.findViewById(R.id.contents_tv);
        }
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.review_item, viewGroup, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder reviewAdapterViewHolder, int position) {
        reviewAdapterViewHolder.authorTv.setText(mReviewList.get(position).getAuthor());
        reviewAdapterViewHolder.contentsTv.setText(mReviewList.get(position).getContents());
    }

    @Override
    public int getItemCount() {
        if (null == mReviewList) return 0;
        return mReviewList.size();
    }

    //Method to refresh the data
    public void setReviewList(List<Review> reviewList){
        mReviewList = reviewList;
        notifyDataSetChanged();
    }
}
