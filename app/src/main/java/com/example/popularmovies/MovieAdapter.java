package com.example.popularmovies;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.data.Movie;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    Movie[] movies;
    private final MovieAdapterOnClickHandler mClickHandler;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler){
        //this.movies=movies;
        mClickHandler=clickHandler;
    }
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int posterLayout=R.layout.movie_poster;
        LayoutInflater inflater= LayoutInflater.from(context);
        View posterView=inflater.inflate(posterLayout, parent,false);

        return new MovieViewHolder(posterView);


    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        //Use Picasso
        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185/";
        Picasso.get() .load(IMAGE_BASE_URL +movies[position].getPosterPath()).placeholder(R.drawable.ic_movie_icon).resize(width/2,0).into(holder.moviePosterImageView);
    }

    @Override
    public int getItemCount() {
        if(movies!=null)
            return movies.length;
        else
            return 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePosterImageView;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePosterImageView=itemView.findViewById(R.id.tv_movie_poster);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            int adapterPosition=getAdapterPosition();
            Movie movieDetails=movies[adapterPosition];
            mClickHandler.onClick(movieDetails);
        }
    }
    public void setMoviesData(String jsonData) {
        Gson gson=new Gson();
        //JSONObject json=new JSONObject(jsonData);
        JsonObject jsonObject = new Gson().fromJson(jsonData, JsonObject.class);
        JsonElement results=jsonObject.get("results");
        Log.i("MovieAdapter",results.toString());
        movies = gson.fromJson(results.toString(),Movie[].class);

        notifyDataSetChanged();
    }
}
