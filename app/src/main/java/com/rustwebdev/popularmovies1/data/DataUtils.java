package com.rustwebdev.popularmovies1.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.rustwebdev.popularmovies1.models.Movie;
import com.rustwebdev.popularmovies1.provider.MoviesContract;

/**
 * Created by flanhelsinki on 10/11/17, Part of PopularMo viesRefinal
 * .
 */

public class DataUtils {
  public static ContentValues getContentValuesFavMovie(Movie movie) {
    ContentValues cv = new ContentValues();
    cv.put(MoviesContract.FavoritesEntry.COLUMN_MOVIE_ID, movie.getId());
    cv.put(MoviesContract.FavoritesEntry.COLUMN_TITLE, movie.getTitle());
    cv.put(MoviesContract.FavoritesEntry.COLUMN_OVERVIEW, movie.getOverview());
    cv.put(MoviesContract.FavoritesEntry.COLUMN_POSTER, movie.getPosterPath());
    cv.put(MoviesContract.FavoritesEntry.COLUMN_BACKDROP, movie.getBackdropPath());
    cv.put(MoviesContract.FavoritesEntry.COLUMN_RATING, movie.getVoteAverage() + "");
    cv.put(MoviesContract.FavoritesEntry.COLUMN_RELEASE, movie.getReleaseDate());
    return cv;
  }

  public static long addFavCvToProvider(ContentValues movieCv, Context context) {
    long movieId;
    Uri insertedUri =
        context.getContentResolver().insert(MoviesContract.FavoritesEntry.CONTENT_URI, movieCv);
    movieId = ContentUris.parseId(insertedUri);
    return movieId;
  }

}
