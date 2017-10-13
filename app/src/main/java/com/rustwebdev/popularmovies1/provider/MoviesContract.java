package com.rustwebdev.popularmovies1.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {

  static final String CONTENT_AUTHORITY = "com.rustwebdev.popularmovies1";
  private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
  static final String PATH_POP_MOVIE = "pop_movies";
  static final String PATH_RATING_MOVIE = "rating_movies";
  static final String PATH_FAVORITES = "favorites";

  public static final class PopularMovieEntry implements BaseColumns {
    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_POP_MOVIE).build();

    static final String CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POP_MOVIE;

    static final String TABLE_NAME = "pop_movies";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_RELEASE = "released_date";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_BACKDROP = "backdrop_url";
    public static final String COLUMN_POSTER = "poster_url";

    static Uri buildPopMoviesUri(long id) {
      return ContentUris.withAppendedId(CONTENT_URI, id);
    }
  }

  public static final class RatingMovieEntry implements BaseColumns {
    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_RATING_MOVIE).build();

    static final String CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RATING_MOVIE;

    static final String TABLE_NAME = "rating_movies";

    static Uri buildRatingMoviesUri(long id) {
      return ContentUris.withAppendedId(CONTENT_URI, id);
    }
  }

  public static final class FavoritesEntry implements BaseColumns {
    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

    static final String CONTENT_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;

    static final String TABLE_NAME = "favorites";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_RELEASE = "released_date";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_BACKDROP = "backdrop_url";
    public static final String COLUMN_POSTER = "poster_url";
    static final String COLUMN_ID = "_id";

    static Uri buildFavoritesUri(long id) {
      return ContentUris.withAppendedId(CONTENT_URI, id);
    }
  }
}
