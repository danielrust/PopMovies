package com.rustwebdev.popularmovies1.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;
import com.rustwebdev.popularmovies1.models.Movie;
import com.rustwebdev.popularmovies1.provider.MoviesContract;
import java.util.ArrayList;
import java.util.Vector;

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

  public static void insertContent(Context context, ArrayList<Movie> movies, Uri uri) {
    Vector<ContentValues> cVVector = new Vector<>(movies.size());

    for (Movie movie : movies) {
      ContentValues movieValues = new ContentValues();
      movieValues.put(MoviesContract.PopularMovieEntry.COLUMN_MOVIE_ID, movie.getId());
      movieValues.put(MoviesContract.PopularMovieEntry.COLUMN_BACKDROP, movie.getBackdropPath());
      movieValues.put(MoviesContract.PopularMovieEntry.COLUMN_OVERVIEW, movie.getOverview());
      movieValues.put(MoviesContract.PopularMovieEntry.COLUMN_POSTER, movie.getPosterPath());
      movieValues.put(MoviesContract.PopularMovieEntry.COLUMN_RATING, movie.getVoteAverage());
      movieValues.put(MoviesContract.PopularMovieEntry.COLUMN_TITLE, movie.getTitle());
      movieValues.put(MoviesContract.PopularMovieEntry.COLUMN_RELEASE, movie.getReleaseDate());

      cVVector.add(movieValues);
    }
    int inserted;

    if (cVVector.size() > 0) {
      ContentValues[] cvArray = new ContentValues[cVVector.size()];
      cVVector.toArray(cvArray);
      inserted = context.getContentResolver().bulkInsert(uri, cvArray);
      if (inserted == 0) {
        Toast.makeText(context, "Unable to add new content at this time", Toast.LENGTH_LONG).show();
      }
    }
  }

  public static final String[] MOVIE_COLUMNS = {
      MoviesContract.PopularMovieEntry.COLUMN_MOVIE_ID,
      MoviesContract.PopularMovieEntry.COLUMN_TITLE,
      MoviesContract.PopularMovieEntry.COLUMN_BACKDROP,
      MoviesContract.PopularMovieEntry.COLUMN_POSTER,
      MoviesContract.PopularMovieEntry.COLUMN_OVERVIEW,
      MoviesContract.PopularMovieEntry.COLUMN_RATING,
      MoviesContract.PopularMovieEntry.COLUMN_RELEASE,
  };

  public static final int COL_MOVIE_ID = 0;
  public static final int COL_MOVIE_TITLE = 1;
  public static final int COL_MOVIE_BACKDROP = 2;
  public static final int COL_MOVIE_POSTER = 3;
  public static final int COL_MOVIE_OVERVIEW = 4;
  public static final int COL_MOVIE_RATING = 5;
  public static final int COL_MOVIE_RELEASE = 6;
}
