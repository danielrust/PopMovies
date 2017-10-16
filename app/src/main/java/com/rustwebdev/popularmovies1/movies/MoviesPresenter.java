package com.rustwebdev.popularmovies1.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.rustwebdev.popularmovies1.Constants;
import com.rustwebdev.popularmovies1.data.MovieService;
import com.rustwebdev.popularmovies1.models.Movie;
import com.rustwebdev.popularmovies1.models.MovieResults;
import com.rustwebdev.popularmovies1.provider.MoviesContract;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rustwebdev.popularmovies1.data.DataUtils.insertContent;

public class MoviesPresenter {
  private final MoviesViewContract.View moviesView;
  private final MovieService service;
  public static final String LOG_TAG = MoviesPresenter.class.getSimpleName();

  public MoviesPresenter(MoviesViewContract.View moviesView, MovieService service) {
    this.moviesView = moviesView;
    this.service = service;
  }

  //public void initDataSet(String sort) {
  //
  //  service.getMovieResults(sort, Constants.API_KEY).enqueue(new Callback<MovieResults>() {
  //    @Override public void onResponse(@NonNull Call<MovieResults> call,
  //        @NonNull Response<MovieResults> response) {
  //      ArrayList<Movie> movieList = response.body().getResults();
  //      moviesView.showMovies(movieList);
  //
  //    }
  //
  //    @Override public void onFailure(@NonNull Call<MovieResults> call, @NonNull Throwable t) {
  //      Log.e(LOG_TAG, "Retrofit Failed");
  //      moviesView.showErrorMessage();
  //    }
  //  });
  //}

  public void initDataSet(final Context context) {
    // Popular Movies Endpoint Retrofit Call
    service.getMovieResults(Constants.SORT_POPULAR, Constants.API_KEY)
        .enqueue(new Callback<MovieResults>() {
          @Override public void onResponse(@NonNull Call<MovieResults> call,
              @NonNull Response<MovieResults> response) {
            ArrayList<Movie> movieList = response.body().getResults();
            insertContent(context, movieList, MoviesContract.PopularMovieEntry.CONTENT_URI);
          }

          @Override public void onFailure(@NonNull Call<MovieResults> call, @NonNull Throwable t) {
            Log.e(LOG_TAG, "Retrofit Failed");
            moviesView.showErrorMessage();
          }
        });
    // Highest Rated Movies Endpoint Retrofit Call
    service.getMovieResults(Constants.SORT_RATING, Constants.API_KEY)
        .enqueue(new Callback<MovieResults>() {
          @Override public void onResponse(@NonNull Call<MovieResults> call,
              @NonNull Response<MovieResults> response) {
            ArrayList<Movie> movieList = response.body().getResults();
            insertContent(context, movieList, MoviesContract.RatingMovieEntry.CONTENT_URI);
          }

          @Override public void onFailure(@NonNull Call<MovieResults> call, @NonNull Throwable t) {
            Log.e(LOG_TAG, "Retrofit Failed");
            moviesView.showErrorMessage();
          }
        });
  }
}
