package com.rustwebdev.popularmovies1.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.rustwebdev.popularmovies1.Constants;
import com.rustwebdev.popularmovies1.data.DataUtils;
import com.rustwebdev.popularmovies1.data.MovieService;
import com.rustwebdev.popularmovies1.models.Movie;
import com.rustwebdev.popularmovies1.models.Trailer;
import com.rustwebdev.popularmovies1.models.Review;
import com.rustwebdev.popularmovies1.models.ReviewsResults;
import com.rustwebdev.popularmovies1.models.TrailerResults;
import com.rustwebdev.popularmovies1.provider.MoviesContract;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by flanhelsinki on 10/12/17, Part of PopularMoviesRefinal
 * .
 */

public class MoviePresenter {
  private final MovieContract.View movieView;
  private final MovieService service;

  public static final String LOG_TAG = MoviePresenter.class.getSimpleName();

  public MoviePresenter(MovieContract.View view, MovieService movieService) {
    this.movieView = view;
    this.service = movieService;
  }

  public void addFavorite(Movie movie, Context context) {
    ContentValues movieCv = DataUtils.getContentValuesFavMovie(movie);
    long movieID = DataUtils.addFavCvToProvider(movieCv, context);
    if (movieID > 1) {
      movieView.showSuccessResultSnackbar();
      movieView.changeFavIcon(true);
      movieView.setIsFav(true);
    }
  }

  public void deleteFavorite(Movie movie, Context context) {
    int rowsDeleted = context.getContentResolver()
        .delete(MoviesContract.FavoritesEntry.CONTENT_URI,
            MoviesContract.FavoritesEntry.COLUMN_MOVIE_ID + " =?", new String[] {
                String.valueOf(movie.getId())
            });
    if (rowsDeleted > 0) {
      movieView.changeFavIcon(false);
      movieView.setIsFav(false);
    }
  }

  public boolean isThisMovieFavorite(Context context, Movie movie) {
    boolean isFav;
    Cursor cursor = context.getContentResolver()
        .query(MoviesContract.FavoritesEntry.CONTENT_URI,
            new String[] { MoviesContract.FavoritesEntry.COLUMN_MOVIE_ID },
            MoviesContract.FavoritesEntry.COLUMN_MOVIE_ID + " =?",
            new String[] { String.valueOf(movie.getId()) }, null);
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        isFav = true;
      } else {
        isFav = false;
      }
      cursor.close();
    } else {
      throw new UnsupportedOperationException("Unable to use cursor");
    }
    return isFav;
  }

  public void getTrailers(Movie movie) {
    service.getTrailer(movie.getId(), Constants.API_KEY).enqueue(new Callback<TrailerResults>() {
      @Override
      public void onResponse(Call<TrailerResults> call, Response<TrailerResults> response) {
        TrailerResults tr = response.body();
        ArrayList<Trailer> mtr = (ArrayList<Trailer>) tr.getResults();
        movieView.showTrailers(mtr);
        Log.d(LOG_TAG, mtr.toString());
      }

      @Override public void onFailure(Call<TrailerResults> call, Throwable t) {
        Log.d(LOG_TAG, "Retrofit has failed: " + t.getMessage());
      }
    });
  }

  public void playTrailer(Movie movie) {
    service.getTrailer(movie.getId(), Constants.API_KEY).enqueue(new Callback<TrailerResults>() {
      @Override
      public void onResponse(Call<TrailerResults> call, Response<TrailerResults> response) {
        TrailerResults tr = response.body();
        List<Trailer> mtr = tr.getResults();
        String key = mtr.get(0).getKey();
        movieView.showTrailer(key);
      }

      @Override public void onFailure(Call<TrailerResults> call, Throwable t) {
        Log.d(LOG_TAG, "Retrofit has failed: " + t.getMessage());
      }
    });
  }

  public void getTrailerUrl(Movie movie) {
    service.getTrailer(movie.getId(), Constants.API_KEY).enqueue(new Callback<TrailerResults>() {
      @Override
      public void onResponse(Call<TrailerResults> call, Response<TrailerResults> response) {
        TrailerResults tr = response.body();
        List<Trailer> mtr = tr.getResults();
        String key = mtr.get(0).getKey();
        movieView.sendTrailerUrl(key);
      }

      @Override public void onFailure(Call<TrailerResults> call, Throwable t) {
        Log.d(LOG_TAG, "Retrofit has failed: " + t.getMessage());
      }
    });
  }

  public void getReviews(Movie movie) {
    service.getReviews(movie.getId(), Constants.API_KEY).enqueue(new Callback<ReviewsResults>() {
      @Override
      public void onResponse(Call<ReviewsResults> call, Response<ReviewsResults> response) {
        ReviewsResults reviewsResults = response.body();
        List<Review> reviews = reviewsResults.getResults();
        movieView.showReviews(reviews);
        Log.d(LOG_TAG, reviews.toString());
      }

      @Override public void onFailure(Call<ReviewsResults> call, Throwable t) {
        Log.d(LOG_TAG, "Retrofit has failed: " + t.getMessage());
      }
    });
  }
}
