package com.rustwebdev.popularmovies1.movie;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rustwebdev.popularmovies1.Constants;
import com.rustwebdev.popularmovies1.R;
import com.rustwebdev.popularmovies1.di.Injector;
import com.rustwebdev.popularmovies1.models.Movie;
import com.rustwebdev.popularmovies1.models.Review;
import com.rustwebdev.popularmovies1.models.Trailer;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements MovieContract.View {
  private static final String LOG_TAG = MovieActivity.class.getSimpleName();
  Movie movie;
  MenuItem movieItemMenu;
  MenuItem shareTrailerBtn;
  MoviePresenter moviePresenter;
  public boolean isFav;
  @BindView(R.id.movie_img) ImageView movieImg;
  @BindView(R.id.movie_title) TextView movieTitle;
  @BindView(R.id.movie_year) TextView movieYear;
  @BindView(R.id.movie_rating) TextView movieRating;
  @BindView(R.id.movie_overview) TextView movieOverview;
  @BindView(R.id.movie_activity) NestedScrollView movieActivity;
  @BindView(R.id.review_recycler_view) RecyclerView reviewRv;
  @BindView(R.id.empty_list) TextView emptyListTv;
  @BindView(R.id.empty_trailer_list) TextView emptyTrailerListTv;
  @BindView(R.id.review_pb) ProgressBar reviewPb;
  @BindView(R.id.trailer_adapter_recycler_view) RecyclerView trailerRv;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(LOG_TAG, "onCreateCalled");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie);
    movie = getIntent().getParcelableExtra("movie");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(movie.getTitle());
    ButterKnife.bind(this);
    Picasso.with(this).load(Constants.IMG_PATH + movie.getPosterPath()).into(movieImg);
    movieTitle.setText(movie.getTitle());
    String rating = movie.getVoteAverage() + Constants.VOTE_AVERAGE_CONCAT;
    movieYear.setText(movie.getReleaseDate().substring(0, 4));
    movieRating.setText(rating);
    movieOverview.setText(movie.getOverview());
    moviePresenter = new MoviePresenter(this, Injector.provideMovieService());
    moviePresenter.getReviews(movie);
    moviePresenter.getTrailers(movie);
    isFav = moviePresenter.isThisMovieFavorite(this, movie);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.movie_item_menu, menu);
    movieItemMenu = menu.findItem(R.id.add_favorite);
    shareTrailerBtn = menu.findItem(R.id.share_trailer);
    changeFavIcon(isFav);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        ActivityCompat.finishAfterTransition(this);
        return true;
      case R.id.add_favorite:
        if (isFav) {
          moviePresenter.deleteFavorite(movie, this);
        } else {
          moviePresenter.addFavorite(movie, this);
        }
        break;
      case R.id.share_trailer:
        moviePresenter.getTrailerUrl(movie);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void showSuccessResultSnackbar() {

    Snackbar.make(movieActivity, "\"" + movie.getTitle() + "\" has been added to your Favorites ",
        Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
      @Override public void onClick(View v) {
        moviePresenter.deleteFavorite(movie, getBaseContext());
      }
    }).show();
  }

  @Override public void changeFavIcon(boolean b) {
    if (b) {
      movieItemMenu.setIcon(R.drawable.ic_favorite_white_24dp);
    } else {
      movieItemMenu.setIcon(R.drawable.ic_favorite_border_white_24dp);
    }
  }

  @Override public void setIsFav(boolean b) {
    isFav = b;
  }

  @Override public void showReviews(List<Review> reviews) {
    reviewPb.setVisibility(View.INVISIBLE);
    if (!reviews.isEmpty()) {
      ReviewAdapter reviewAdapter = new ReviewAdapter(reviews);
      RecyclerView.LayoutManager mLayoutManager =
          new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
      reviewRv.setAdapter(reviewAdapter);
      reviewRv.setLayoutManager(mLayoutManager);
      reviewRv.setNestedScrollingEnabled(false);
    } else {
      reviewRv.setVisibility(View.GONE);
      emptyListTv.setVisibility(View.VISIBLE);
    }
  }

  @Override public void showTrailer(String key) {
    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_URL_PREFIX + key));
    try {
      startActivity(Intent.createChooser(i, "Choose your preferred application to open video"));
    } catch (ActivityNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override public void sendTrailerUrl(String key) {
    String mimeType = "text/plain";
    String title = "Send the trailer to a friend";
    String youtubeUrl = Constants.YOUTUBE_URL_PREFIX + key;
    Intent shareTrailerUrl = ShareCompat.IntentBuilder.from(this)
        .setChooserTitle(title)
        .setType(mimeType)
        .setText("Check out this awesome trailer for " + movie.getTitle() + "! " + youtubeUrl)
        .getIntent();
    startActivity(shareTrailerUrl);
  }

  @Override public void showTrailers(ArrayList<Trailer> trailers) {

    if (!trailers.isEmpty()) {
      TrailerAdapter trailerAdapter = new TrailerAdapter(trailers, itemListener, this);
      RecyclerView.LayoutManager mLayoutManager =
          new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
      trailerRv.setLayoutManager(mLayoutManager);
      trailerRv.setAdapter(trailerAdapter);
      trailerRv.setNestedScrollingEnabled(false);
    } else {
      trailerRv.setVisibility(View.GONE);
      emptyTrailerListTv.setVisibility(View.VISIBLE);
    }
  }

  private TrailerAdapter.TrailerItemListener itemListener =
      new TrailerAdapter.TrailerItemListener() {
        @Override public void onTrailerClick(String key) {
          showTrailer(key);
        }
      };

  @Override protected void onStop() {
    super.onStop();
    reviewPb.setVisibility(View.INVISIBLE);
  }
}
