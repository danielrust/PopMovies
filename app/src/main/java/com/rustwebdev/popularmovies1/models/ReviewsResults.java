package com.rustwebdev.popularmovies1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;


public class ReviewsResults {

  @SerializedName("results") @Expose private ArrayList<Review> results;

  /**
   * @return The results
   */
  public ArrayList<Review> getResults() {
    return results;
  }
}
