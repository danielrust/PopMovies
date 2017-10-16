package com.rustwebdev.popularmovies1.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rustwebdev.popularmovies1.R;
import com.rustwebdev.popularmovies1.models.Trailer;
import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
  private static final String LOG_TAG = TrailerAdapter.class.getSimpleName();
  private ArrayList<Trailer> trailers = new ArrayList<>();
  private TrailerItemListener trailerItemListener;
  private Context context;

  public TrailerAdapter(List<Trailer> trailers, TrailerItemListener trailerItemListener, Context context) {
    this.trailerItemListener = trailerItemListener;
    this.trailers = (ArrayList<Trailer>) trailers;
    this.context = context;
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TrailerItemListener trailerItemListener;
    @BindView(R.id.trailer_btn_text) TextView trailerTxt;

    @Override public void onClick(View view) {
      Trailer trailer = getItem(getAdapterPosition());
      this.trailerItemListener.onTrailerClick(trailer.getKey());
    }

    ViewHolder(View v, TrailerItemListener itemListener) {
      super(v);
      ButterKnife.bind(this, v);
      this.trailerItemListener = itemListener;
      v.setOnClickListener(this);
    }
  }

  @Override public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.trailer_list_item, viewGroup, false);
    return new TrailerAdapter.ViewHolder(v, this.trailerItemListener);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, final int position) {
    Trailer trailer = trailers.get(position);
    viewHolder.trailerTxt.setText(context.getString(R.string.trailer_text, trailer.getName()));
    //viewHolder.trailerTxt.setText("View " + trailer.getName());
  }

  @Override public int getItemCount() {
    return trailers.size();
  }

  private Trailer getItem(int adapterPosition) {
    return trailers.get(adapterPosition);
  }

  public interface TrailerItemListener {
    void onTrailerClick(String key);
  }
}

