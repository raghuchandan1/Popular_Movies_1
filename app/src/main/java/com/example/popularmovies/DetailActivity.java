package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("Movie");
        //setTitle(movie.getOriginalTile());
        TextView movieTitleView = (TextView) findViewById(R.id.tv_movie_title_detail);
        ImageView moviePosterView = (ImageView) findViewById(R.id.tv_movie_poster_detail);
        TextView movieReleaseDateView = (TextView) findViewById(R.id.tv_release_date_detail);
        TextView movieVoteAverageView = (TextView) findViewById(R.id.tv_vote_average_detail);
        TextView moviePlotSynopsisView = (TextView) findViewById(R.id.tv_plot_synopsis_detail);
        if (!getIntent().hasExtra("Movie")) {
            closeOnError();
            return;
        }
        if(movie==null){
            closeOnError();
            return;
        }
        String movieTitle=movie.getOriginalTile();
        Log.i("DetailActivity",movieTitle);
        movieTitleView.setText(movie.getOriginalTile());
        movieReleaseDateView.setText(movie.getReleaseDate());
        movieVoteAverageView.setText(Double.toString(movie.getVoteAverage()));
        moviePlotSynopsisView.setText(movie.getOverview());
        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        //int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342/";
        Picasso.get() .load(IMAGE_BASE_URL +movie.getPosterPath()).placeholder(R.drawable.ic_movie_icon).resize(width,0).into(moviePosterView);

    }
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}
