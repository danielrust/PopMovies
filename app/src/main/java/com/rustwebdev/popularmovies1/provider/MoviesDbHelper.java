package com.rustwebdev.popularmovies1.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class MoviesDbHelper extends SQLiteOpenHelper {
  private static final int DATABASE_VERSION = 2;
  private static final String DATABASE_NAME = "movies.db";
  private static final String LOG_TAG = MoviesDbHelper.class.getSimpleName();

  MoviesDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {

    final String SQL_CREATE_FAVORITES_TABLE =
        "CREATE TABLE " + MoviesContract.FavoritesEntry.TABLE_NAME + " (" +
            MoviesContract.FavoritesEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
            MoviesContract.FavoritesEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE  , " +
            MoviesContract.FavoritesEntry.COLUMN_TITLE + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_BACKDROP + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_POSTER + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_OVERVIEW + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_RATING + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_RELEASE + " TEXT " +
            " );";

    final String SQL_CREATE_POPULAR_MOVIES_TABLE =
        "CREATE TABLE " + MoviesContract.PopularMovieEntry.TABLE_NAME + " (" +
            MoviesContract.FavoritesEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
            MoviesContract.FavoritesEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE  , " +
            MoviesContract.FavoritesEntry.COLUMN_TITLE + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_BACKDROP + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_POSTER + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_OVERVIEW + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_RATING + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_RELEASE + " TEXT " +
            " );";

    final String SQL_CREATE_RATING_MOVIES_TABLE =
        "CREATE TABLE " + MoviesContract.RatingMovieEntry.TABLE_NAME + " (" +
            MoviesContract.FavoritesEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
            MoviesContract.FavoritesEntry.COLUMN_MOVIE_ID + " INTEGER UNIQUE  , " +
            MoviesContract.FavoritesEntry.COLUMN_TITLE + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_BACKDROP + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_POSTER + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_OVERVIEW + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_RATING + " TEXT , " +
            MoviesContract.FavoritesEntry.COLUMN_RELEASE + " TEXT " +
            " );";

    db.execSQL(SQL_CREATE_FAVORITES_TABLE);
    db.execSQL(SQL_CREATE_POPULAR_MOVIES_TABLE);
    db.execSQL(SQL_CREATE_RATING_MOVIES_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.FavoritesEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.PopularMovieEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.RatingMovieEntry.TABLE_NAME);
    onCreate(db);
  }
}
