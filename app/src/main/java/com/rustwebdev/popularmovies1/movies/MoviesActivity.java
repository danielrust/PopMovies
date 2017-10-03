package com.rustwebdev.popularmovies1.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rustwebdev.popularmovies1.Constants;
import com.rustwebdev.popularmovies1.R;
import com.rustwebdev.popularmovies1.data.Injector;
import com.rustwebdev.popularmovies1.models.Movie;
import java.util.ArrayList;



public class MoviesActivity extends AppCompatActivity implements MoviesContract.View {
  private static final String LOG_TAG = MoviesActivity.class.getSimpleName();
  @BindView(R.id.movies_rv) RecyclerView movies_rv;
  MenuItem popMenuItem;
  MenuItem ratingMenuItem;
  private MoviesAdapter moviesAdapter;
  private ArrayList<Movie> movieList;
  MoviesPresenter moviesPresenter;
  int currentSort = 1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movies);

    ButterKnife.bind(this);

    moviesPresenter = new MoviesPresenter(this, Injector.provideMovieService());
    moviesAdapter = new MoviesAdapter(this, new ArrayList<Movie>(0), itemListener);
    if (savedInstanceState != null) {
      if (savedInstanceState.containsKey("movieList")) {
        showMovies(savedInstanceState.<Movie>getParcelableArrayList("movieList"));
        currentSort = savedInstanceState.getInt("currentSort");
      }
    } else {
      moviesPresenter.initDataSet(Constants.SORT_POPULAR);
    }
    configureLayout();
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

  }

  private MoviesAdapter.MovieItemListener itemListener = new MoviesAdapter.MovieItemListener() {
    @Override public void onMovieClick(long id) {
      Log.d(LOG_TAG, String.valueOf(id));
    }
  };

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelableArrayList("movieList", movieList);
    outState.putInt("currentSort",currentSort);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    popMenuItem = menu.findItem(R.id.sort_popular);
    ratingMenuItem = menu.findItem(R.id.sort_rating);
    if (currentSort == 2){
      popMenuItem.setChecked(false);
      ratingMenuItem.setChecked(true);
    }
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.sort_popular:
        moviesPresenter.initDataSet(Constants.SORT_POPULAR);
        popMenuItem.setChecked(true);
        ratingMenuItem.setChecked(false);
        currentSort = 1;
        break;
      case R.id.sort_rating:
        moviesPresenter.initDataSet(Constants.SORT_RATING);
        popMenuItem.setChecked(false);
        ratingMenuItem.setChecked(true);
        currentSort = 2;
        break;

      default:
        return super.onOptionsItemSelected(item);
    }
    return true;
  }
}
