package com.example.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

// Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
public class Movie implements Parcelable {
    private String title;
    private String release_date;
    private String poster_path;
    private double vote_average;
    private String overview;

    protected Movie(Parcel in) {
        title = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void setOriginalTile(String original_title) {
        this.title = original_title;
    }
    public String getOriginalTile() {
        return title;
    }
    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }
    public String getReleaseDate() {
        return release_date;
    }
    public String getPosterPath() {
        return poster_path;
    }
    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }
    public double getVoteAverage() {
        return vote_average;
    }
    public void setVoteAverage(double vote_average) {
        this.vote_average = vote_average;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(release_date);
        dest.writeString(poster_path);
        dest.writeDouble(vote_average);
        dest.writeString(overview);
    }
}
