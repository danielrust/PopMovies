package com.rustwebdev.popularmovies1.models;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;


public class TrailerResults {
  @Expose
  private final List<Trailer> results = new ArrayList<>();

  public List<Trailer> getResults() {
    return results;
  }
}
