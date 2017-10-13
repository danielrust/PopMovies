package com.rustwebdev.popularmovies1.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class Review implements Parcelable {

  private final String id;
  private final String author;
  private final String content;
  private final String url;
  private Map<String, Object> additionalProperties = new HashMap<>();

  /**
   * @return The id
   */
  public String getId() {
    return id;
  }

  /**
   * @return The author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * @return The content
   */
  public String getContent() {
    return content;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.author);
    dest.writeString(this.content);
    dest.writeString(this.url);
    dest.writeInt(this.additionalProperties.size());
    for (Map.Entry<String, Object> entry : this.additionalProperties.entrySet()) {
      dest.writeString(entry.getKey());
      dest.writeParcelable((Parcelable) entry.getValue(), flags);
    }
  }

  private Review(Parcel in) {
    this.id = in.readString();
    this.author = in.readString();
    this.content = in.readString();
    this.url = in.readString();
    int additionalPropertiesSize = in.readInt();
    this.additionalProperties = new HashMap<>(additionalPropertiesSize);
    for (int i = 0; i < additionalPropertiesSize; i++) {
      String key = in.readString();
      Object value = in.readParcelable(Object.class.getClassLoader());
      this.additionalProperties.put(key, value);
    }
  }

  public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
    @Override public Review createFromParcel(Parcel source) {
      return new Review(source);
    }

    @Override public Review[] newArray(int size) {
      return new Review[size];
    }
  };
}