package com.rustwebdev.popularmovies1;

import com.rustwebdev.popularmovies1.models.MovieResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MovieService {

  @GET("movie") Call<MovieResults> getMovieResults(@Query("api_key") String key, @Query("sort_by") String sort, @Query("vote_count.gte") String vote_count);
}
