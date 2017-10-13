package com.rustwebdev.popularmovies1.movies;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rustwebdev.popularmovies1.Constants;
import com.rustwebdev.popularmovies1.R;
import com.rustwebdev.popularmovies1.di.Injector;
import com.rustwebdev.popularmovies1.models.Movie;
import com.rustwebdev.popularmovies1.movie.MovieActivity;
import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity
    implements MoviesContract.View, LoaderManager.LoaderCallbacks<Cursor> {
  private static final String LOG_TAG = MoviesActivity.class.getSimpleName();

  public static final String MOVIE_LIST_BUNDLE_KEY = "movieList";
  public static final String CURRENT_SORT_BUNDLE_KEY = "currentSort";
  @BindView(R.id.movies_rv) RecyclerView movies_rv;
  @BindView(R.id.pb) ProgressBar pb;
  MenuItem popMenuItem;
  MenuItem ratingMenuItem;
  MenuItem favoritesMenuItem;
  private MoviesAdapter moviesAdapter;
  private ArrayList<Movie> movieList;
  MoviesPresenter moviesPresenter;
  int currentSort = 1;
  private static final int ID_FORECAST_LOADER = 44;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies);
    getWindow().setExitTransition(new Explode());
    ButterKnife.bind(this);

    moviesPresenter = new MoviesPresenter(this, Injector.provideMovieService());
    moviesAdapter = new MoviesAdapter(this, new ArrayList<Movie>(0), itemListener);
    if (savedInstanceState != null) {
      if (savedInstanceState.containsKey(MOVIE_LIST_BUNDLE_KEY)) {
        showMovies(savedInstanceState.<Movie>getParcelableArrayList(MOVIE_LIST_BUNDLE_KEY));
        currentSort = savedInstanceState.getInt(CURRENT_SORT_BUNDLE_KEY);
      }
    } else {
      moviesPresenter.initDataSet(Constants.SORT_POPULAR);
    }
    configureLayout();
    getSupportLoaderManager().initLoader(ID_FORECAST_LOADER, null, this);
  }

  private void configureLayout() {
    movies_rv.setAdapter(moviesAdapter);
    movies_rv.setHasFixedSize(true);
  }

  @Override public void showMovies(ArrayList<Movie> movies) {
    moviesAdapter.updateMovies(movies);
    this.movieList = movies;
  }

  @Override public void showErrorMessage() {
    Toast.makeText(this, Constants.RETROFIT_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
  }

  private MoviesAdapter.MovieItemListener itemListener = new MoviesAdapter.MovieItemListener() {
    @Override public void onMovieClick(Movie movie, ImageView imgView) {
      Intent intent = new Intent(MoviesActivity.this, MovieActivity.class);
      intent.putExtra("movie", movie);
      ActivityOptions options =
          ActivityOptions.makeSceneTransitionAnimation(MoviesActivity.this, imgView,
              Constants.MOVIE_IMG_TRANS_SHARED_ELEMENT);

      startActivity(intent, options.toBundle());
    }
  };

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelableArrayList(MOVIE_LIST_BUNDLE_KEY, movieList);
    outState.putInt(CURRENT_SORT_BUNDLE_KEY, currentSort);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    popMenuItem = menu.findItem(R.id.sort_popular);
    ratingMenuItem = menu.findItem(R.id.sort_rating);
    favoritesMenuItem = menu.findItem(R.id.sort_favorite);
    if (currentSort == 2) {
      popMenuItem.setChecked(false);
      ratingMenuItem.setChecked(true);
      favoritesMenuItem.setChecked(false);
    } else if (currentSort == 3) {
      popMenuItem.setChecked(false);
      ratingMenuItem.setChecked(false);
      favoritesMenuItem.setChecked(true);
    }
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.sort_popular:
        if (currentSort == 1) {
          break;
        }
        moviesPresenter.initDataSet(Constants.SORT_POPULAR);
        popMenuItem.setChecked(true);
        ratingMenuItem.setChecked(false);
        favoritesMenuItem.setChecked(false);

        currentSort = 1;
        break;
      case R.id.sort_rating:
        if (currentSort == 2) {
          break;
        }
        moviesPresenter.initDataSet(Constants.SORT_RATING);
        popMenuItem.setChecked(false);
        ratingMenuItem.setChecked(true);
        favoritesMenuItem.setChecked(false);
        currentSort = 2;
        break;
      case R.id.sort_favorite:
        if (currentSort == 3) {
          break;
        }
        moviesPresenter.getFavorites();
        popMenuItem.setChecked(false);
        ratingMenuItem.setChecked(false);
        favoritesMenuItem.setChecked(true);
        currentSort = 3;
        break;
      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return null;
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

  }

  @Override public void onLoaderReset(Loader<Cursor> loader) {

  }
}
