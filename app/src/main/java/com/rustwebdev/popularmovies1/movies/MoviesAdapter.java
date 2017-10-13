package com.rustwebdev.popularmovies1.movies;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rustwebdev.popularmovies1.Constants;
import com.rustwebdev.popularmovies1.R;
import com.rustwebdev.popularmovies1.data.DataUtils;
import com.rustwebdev.popularmovies1.models.Movie;
import com.squareup.picasso.Picasso;
import java.util.List;

class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
  private List<Movie> movies;
  private MovieItemListener itemListener;
  private Context context;
  private Drawable placeholder;
  private Cursor mCursor;

  public MoviesAdapter(Context context, List<Movie> movies, MovieItemListener itemListener) {
    this.context = context;
    this.movies = movies;
    this.itemListener = itemListener;
    placeholder = context.getDrawable(R.mipmap.ic_launcher_foreground);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
    return new ViewHolder(v, this.itemListener);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    mCursor.moveToPosition(position);
    holder.movieViewTv.setText(mCursor.getString(DataUtils.COL_MOVIE_TITLE));
    Picasso.with(context)
        .load(Constants.IMG_PATH + mCursor.getString(DataUtils.COL_MOVIE_POSTER))
        .placeholder(placeholder)
        .error(placeholder)
        .into(holder.movieViewImg);
  }

  @Override public int getItemCount() {
    if (null == mCursor) {
      return 0;
    } else {
      return mCursor.getCount();
    }
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.movie_item_img) ImageView movieViewImg;
    @BindView(R.id.movie_item_tv) TextView movieViewTv;
    MovieItemListener itemListener;

    public ViewHolder(View itemView, MovieItemListener itemListener) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      this.itemListener = itemListener;
      itemView.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
      Movie movie = getItem(getAdapterPosition());
      this.itemListener.onMovieClick(movie, movieViewImg);
    }
  }

  private Movie getItem(int adapterPosition) {
    mCursor.moveToPosition(adapterPosition);
    return new Movie(mCursor.getInt(DataUtils.COL_MOVIE_ID),
        mCursor.getDouble(DataUtils.COL_MOVIE_RATING), mCursor.getString(DataUtils.COL_MOVIE_TITLE),
        mCursor.getString(DataUtils.COL_MOVIE_POSTER),
        mCursor.getString(DataUtils.COL_MOVIE_OVERVIEW),
        mCursor.getString(DataUtils.COL_MOVIE_RELEASE));
  }

  public void updateMovies(List<Movie> movies) {
    this.movies = movies;
    notifyDataSetChanged();
  }

  void swapCursor(Cursor newCursor) {
    mCursor = newCursor;
    notifyDataSetChanged();
    //mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
  }

  public interface MovieItemListener {
    void onMovieClick(Movie movie, ImageView imageView);
  }
}
