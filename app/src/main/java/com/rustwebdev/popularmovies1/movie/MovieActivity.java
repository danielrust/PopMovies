package com.rustwebdev.popularmovies1.movie;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rustwebdev.popularmovies1.Constants;
import com.rustwebdev.popularmovies1.R;
import com.rustwebdev.popularmovies1.models.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by flanhelsinki on 10/2/17.
 */

public class MovieActivity extends Activity implements MovieContract.View {
  Movie movie;
  @BindView(R.id.movie_img) ImageView movieImg;
  @BindView(R.id.movie_title) TextView movieTitle;
  @BindView(R.id.movie_year) TextView movieYear;
  @BindView(R.id.movie_rating) TextView movieRating;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie);
    movie = getIntent().getParcelableExtra("movie");
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setTitle(movie.getTitle());
    ButterKnife.bind(this);
    Picasso.with(this).load(Constants.IMG_PATH + movie.getPosterPath()).into(movieImg);
    movieTitle.setText(movie.getTitle());
    movieYear.setText(movie.getReleaseDate().substring(0, 4));
    movieRating.setText(movie.getVoteAverage().toString() + "/10");
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
       ActivityCompat.finishAfterTransition(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
