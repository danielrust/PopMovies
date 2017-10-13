package com.rustwebdev.popularmovies1.movie;

import com.rustwebdev.popularmovies1.models.Review;
import java.util.List;

/**
 * Created by flanhelsinki on 10/12/17, Part of PopularMoviesRefinal
 * .
 */

public interface MovieContract {

  interface View {
    void showSuccessResultSnackbar();
    void changeFavIcon(boolean b);
    void setIsFav(boolean b);
    void showTrailer(String key);
    void showReviews(List<Review> reviews);
  }
}
