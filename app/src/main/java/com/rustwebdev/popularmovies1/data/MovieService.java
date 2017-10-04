package com.rustwebdev.popularmovies1.data;

import com.rustwebdev.popularmovies1.models.MovieResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

  @GET("{endpoint}") Call<MovieResults> getMovieResults(@Path("endpoint") String endpoint, @Query("api_key") String key);
}
