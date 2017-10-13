package com.rustwebdev.popularmovies1.movie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rustwebdev.popularmovies1.R;
import com.rustwebdev.popularmovies1.models.Review;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flanhelsinki on 10/13/17, Part of PopularMoviesRefinal
 * .
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
  private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();
  private ArrayList<Review> reviews = new ArrayList<>();

  class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.reviewName) TextView reviewName;
    @BindView(R.id.reviewText) TextView reviewText;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }
  }

  public ReviewAdapter(List<Review> reviews) {
    this.reviews = (ArrayList<Review>) reviews;
  }

  @Override public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.review_list_item, viewGroup, false);
    return new ReviewAdapter.ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, final int position) {
    Review review = reviews.get(position);
    viewHolder.reviewName.setText(review.getAuthor());
    viewHolder.reviewText.setText(review.getContent());
  }

  @Override public int getItemCount() {
    return reviews.size();
  }


  }

