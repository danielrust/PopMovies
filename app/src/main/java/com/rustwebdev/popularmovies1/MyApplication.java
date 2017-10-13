package com.rustwebdev.popularmovies1;

import android.app.Application;
import com.facebook.stetho.Stetho;

/**
 * Created by flanhelsinki on 10/11/17, Part of PopularMoviesRefinal
 * .
 */

public class MyApplication extends Application {
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
  }
}
