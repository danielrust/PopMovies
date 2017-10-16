package com.rustwebdev.popularmovies1;

public class Constants {
  public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
  public static final String API_KEY = BuildConfig.MOVIE_DB_API_TOKEN;
  public static final String SORT_POPULAR = "popular";
  public static final String SORT_RATING = "top_rated";
  public static final String IMG_PATH = "http://image.tmdb.org/t/p/w185/";
  public static final String VOTE_AVERAGE_CONCAT = "/10";
  public static final String MOVIE_IMG_TRANS_SHARED_ELEMENT = "movieImgTrans";
  public static final String RETROFIT_ERROR_MESSAGE =
      "We're sorry, the service is unavailable right now. Please try again later.";
  public static final String YOUTUBE_URL_PREFIX = "http://www.youtube.com/watch?v=";
}

