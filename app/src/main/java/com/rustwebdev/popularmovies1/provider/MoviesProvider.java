package com.rustwebdev.popularmovies1.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by flanhelsinki on 11/15/16, Part of PopularMoviesRefinal
 * .
 */

public class MoviesProvider extends ContentProvider {
  private static final UriMatcher sUriMatcher = buildUriMatcher();

  private MoviesDbHelper mDBHelper;
  private static final int FAVORITES = 200;

  @Override public boolean onCreate() {
    mDBHelper = new MoviesDbHelper(getContext());
    mDBHelper.getWritableDatabase();
    return true;
  }

  @Nullable @Override public Cursor query(@NonNull Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {
    Cursor retCursor;
    switch (sUriMatcher.match(uri)) {

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

      case FAVORITES:
        return MoviesContract.FavoritesEntry.CONTENT_TYPE;

      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
  }

  @Nullable @Override public Uri insert(@NonNull Uri uri, ContentValues values) {
    final SQLiteDatabase db = mDBHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);
    Uri returnUri;

    switch (match) {

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
      case FAVORITES:
        rowsDeleted = db.delete(MoviesContract.FavoritesEntry.TABLE_NAME, selection, selectionArgs);
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
    uriMatcher.addURI(authority, MoviesContract.PATH_FAVORITES, FAVORITES);
    return uriMatcher;
  }
}
