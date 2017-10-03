package com.rustwebdev.popularmovies1.data;

import com.rustwebdev.popularmovies1.Constants;
import com.rustwebdev.popularmovies1.MovieService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Injector {

  public static Retrofit provideRetrofit(String baseUrl) {
    return new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static MovieService provideMovieService(){
    return provideRetrofit(Constants.BASE_URL).create(MovieService.class);
  }
}
