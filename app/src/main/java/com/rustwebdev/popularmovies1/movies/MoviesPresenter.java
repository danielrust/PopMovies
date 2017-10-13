package com.rustwebdev.popularmovies1.movies;

import android.support.annotation.NonNull;
import android.util.Log;
import com.rustwebdev.popularmovies1.Constants;
import com.rustwebdev.popularmovies1.data.MovieService;
import com.rustwebdev.popularmovies1.models.Movie;
import com.rustwebdev.popularmovies1.models.MovieResults;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesPresenter {
  private final MoviesContract.View moviesView;
  private final MovieService service;
  public static final String LOG_TAG = MoviesPresenter.class.getSimpleName();

  public MoviesPresenter(MoviesContract.View moviesView, MovieService service) {
    this.moviesView = moviesView;
    this.service = service;
  }

  public void initDataSet(String sort) {

    service.getMovieResults(sort, Constants.API_KEY).enqueue(new Callback<MovieResults>() {
      @Override public void onResponse(@NonNull Call<MovieResults> call,
          @NonNull Response<MovieResults> response) {
        ArrayList<Movie> movieList = response.body().getResults();
        moviesView.showMovies(movieList);
      }

      @Override public void onFailure(@NonNull Call<MovieResults> call, @NonNull Throwable t) {
        Log.e(LOG_TAG, "Retrofit Failed");
        moviesView.showErrorMessage();
      }
    });
  }

  public void getFavorites() {

  }
}
