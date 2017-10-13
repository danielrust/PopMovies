package com.rustwebdev.popularmovies1.data;

import com.rustwebdev.popularmovies1.models.MovieResults;
import com.rustwebdev.popularmovies1.models.ReviewsResults;
import com.rustwebdev.popularmovies1.models.TrailerResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

  @GET("{endpoint}") Call<MovieResults> getMovieResults(@Path("endpoint") String endpoint,
      @Query("api_key") String key);

  @GET("{id}/videos") Call<TrailerResults> getTrailer(@Path("id") int id,
      @Query("api_key") String api_key);

  @GET("{id}/reviews") Call<ReviewsResults> getReviews(@Path("id") int id,
      @Query("api_key") String api_key);
}
