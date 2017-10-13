package com.rustwebdev.popularmovies1.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MoviesProvider extends ContentProvider {
  private static final UriMatcher sUriMatcher = buildUriMatcher();

  private MoviesDbHelper mDBHelper;
  private static final int POP_MOVIES = 100;
  private static final int FAVORITES = 200;
  private static final int RATING_MOVIES = 300;

  @Override public boolean onCreate() {
    mDBHelper = new MoviesDbHelper(getContext());
    mDBHelper.getWritableDatabase();
    return true;
  }

  @Nullable @Override public Cursor query(@NonNull Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {
    Cursor retCursor;
    switch (sUriMatcher.match(uri)) {
      case POP_MOVIES: {
        retCursor = mDBHelper.getReadableDatabase()
            .query(MoviesContract.PopularMovieEntry.TABLE_NAME, projection, selection,
                selectionArgs, null, null, sortOrder);
        break;
      }
      case RATING_MOVIES: {
        retCursor = mDBHelper.getReadableDatabase()
            .query(MoviesContract.RatingMovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);
        break;
      }
      case FAVORITES: {
        retCursor = mDBHelper.getReadableDatabase()
            .query(MoviesContract.FavoritesEntry.TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);
        break;
      }
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    retCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return retCursor;
  }

  @Nullable @Override public String getType(@NonNull Uri uri) {
    final int match = sUriMatcher.match(uri);

    switch (match) {
      case POP_MOVIES:
        return MoviesContract.PopularMovieEntry.CONTENT_TYPE;
      case FAVORITES:
        return MoviesContract.FavoritesEntry.CONTENT_TYPE;
      case RATING_MOVIES:
        return MoviesContract.RatingMovieEntry.CONTENT_TYPE;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
  }

  @Override public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
    final SQLiteDatabase db = mDBHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);
    switch (match) {
      case POP_MOVIES:
        db.beginTransaction();
        int returnCount = 0;
        try {
          for (ContentValues value : values) {
            long _id =
                db.insertWithOnConflict(MoviesContract.PopularMovieEntry.TABLE_NAME, null, value,
                    SQLiteDatabase.CONFLICT_REPLACE);
            if (_id != -1) {
              returnCount++;
            }
          }
          db.setTransactionSuccessful();
        } finally {
          db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;

      case RATING_MOVIES:
        db.beginTransaction();
        int returnCount1 = 0;
        try {
          for (ContentValues value : values) {
            long _id =

                db.replace(MoviesContract.RatingMovieEntry.TABLE_NAME, null, value);
            if (_id != -1) {
              returnCount1++;
            }
          }
          db.setTransactionSuccessful();
        } finally {
          db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount1;
      default:
        return super.bulkInsert(uri, values);
    }
  }

  @Nullable @Override public Uri insert(@NonNull Uri uri, ContentValues values) {
    final SQLiteDatabase db = mDBHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);
    Uri returnUri;

    switch (match) {
      case POP_MOVIES: {
        long _id =
            db.insertWithOnConflict(MoviesContract.PopularMovieEntry.TABLE_NAME, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);
        if (_id > 0) {
          returnUri = MoviesContract.PopularMovieEntry.buildPopMoviesUri(_id);
        } else {
          throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        break;
      }
      case RATING_MOVIES: {
        long _id = db.insertWithOnConflict(MoviesContract.RatingMovieEntry.TABLE_NAME, null, values,
            SQLiteDatabase.CONFLICT_REPLACE);
        if (_id > 0) {
          returnUri = MoviesContract.RatingMovieEntry.buildRatingMoviesUri(_id);
        } else {
          throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        break;
      }
      case FAVORITES: {
        long _id = db.insert(MoviesContract.FavoritesEntry.TABLE_NAME, null, values);
        if (_id > 0) {
          returnUri = MoviesContract.FavoritesEntry.buildFavoritesUri(_id);
        } else {
          throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        break;
      }
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return returnUri;
  }

  @Override public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
    final SQLiteDatabase db = mDBHelper.getWritableDatabase();
    int rowsDeleted;
    final int match = sUriMatcher.match(uri);
    if (null == selection) selection = "1";
    switch (match) {
      case POP_MOVIES:
        rowsDeleted =
            db.delete(MoviesContract.PopularMovieEntry.TABLE_NAME, selection, selectionArgs);
        break;
      case FAVORITES:
        rowsDeleted = db.delete(MoviesContract.FavoritesEntry.TABLE_NAME, selection, selectionArgs);
        break;
      case RATING_MOVIES:
        rowsDeleted =
            db.delete(MoviesContract.RatingMovieEntry.TABLE_NAME, selection, selectionArgs);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    if (rowsDeleted != 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return rowsDeleted;
  }

  @Override public int update(@NonNull Uri uri, ContentValues values, String selection,
      String[] selectionArgs) {
    return 0;
  }

  static UriMatcher buildUriMatcher() {
    final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = MoviesContract.CONTENT_AUTHORITY;
    uriMatcher.addURI(authority, MoviesContract.PATH_POP_MOVIE, POP_MOVIES);
    uriMatcher.addURI(authority, MoviesContract.PATH_FAVORITES, FAVORITES);
    uriMatcher.addURI(authority, MoviesContract.PATH_RATING_MOVIE, RATING_MOVIES);
    return uriMatcher;
  }
}
