package com.rustwebdev.popularmovies1.movies;

import com.rustwebdev.popularmovies1.models.Movie;
import java.util.ArrayList;


public interface MoviesViewContract {

  interface View {
    void showMovies(ArrayList<Movie> movies);

    void showErrorMessage();
  }
}
