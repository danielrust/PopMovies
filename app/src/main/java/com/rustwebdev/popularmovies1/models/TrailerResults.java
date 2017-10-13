package com.rustwebdev.popularmovies1.models;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;


public class TrailerResults {
  @Expose
  private final List<MovieTrailerResults> results = new ArrayList<>();

  public List<MovieTrailerResults> getResults() {
    return results;
  }
}
