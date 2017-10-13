package com.rustwebdev.popularmovies1.models;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flanhelsinki on 1/26/16, Part of PopularMoviesRefinal
 .
 */
public class TrailerResults {
  @Expose
  private final List<MovieTrailerResults> results = new ArrayList<>();

  public List<MovieTrailerResults> getResults() {
    return results;
  }
}
