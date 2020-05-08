package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.data.Movie;
import com.example.popularmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import static com.example.popularmovies.utilities.NetworkUtils.buildUrl;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private static final String TAG = "MainActivity";
    private RecyclerView movieTitlesRecyclerView;
    private MovieAdapter movieAdapter;
    private TextView mErrorMessageDisplay;
    //private MovieAdapter movieAdapter2;
    private ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieTitlesRecyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        mLoadingIndicator=(ProgressBar)findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay=(TextView)findViewById(R.id.tv_error_message_display);
        movieAdapter=new MovieAdapter(this);
        //movieAdapter2=new MovieAdapter(moviesByRating);
        movieTitlesRecyclerView.setAdapter(movieAdapter);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        movieTitlesRecyclerView.setLayoutManager(layoutManager);
        loadMoviesData();
    }
    private void loadMoviesData(String sortBy) {
        showMoviesDataView();
        URL url=buildUrl(sortBy);
        Log.i(TAG,url.toString());

        new MovieDBQueryTask().execute(url);
    }
    private void loadMoviesData() {
        showMoviesDataView();
        URL url=buildUrl();
        Log.i(TAG,url.toString());

        new MovieDBQueryTask().execute(url);
    }
    private void showMoviesDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        movieTitlesRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        movieTitlesRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Context context=this;
        Class destinationClass=DetailActivity.class;
        Intent intentToStart=new Intent(context,destinationClass);
        intentToStart.putExtra("Movie",movie);
        startActivity(intentToStart);
    }

    @SuppressLint("StaticFieldLeak")
    public class MovieDBQueryTask extends AsyncTask<URL, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        // COMPLETED (2) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieDBSearchResults = null;
            try {
                movieDBSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieDBSearchResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String movieDBSearchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieDBSearchResults != null && !movieDBSearchResults.equals("")) {
                showMoviesDataView();
                movieAdapter.setMoviesData(movieDBSearchResults);
            }
            else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemSelected=item.getItemId();
        if(itemSelected==R.id.action_sort_by_popularity){
            //movieAdapter.setMoviesData(null);
            loadMoviesData("popular");
            return true;
        }
        if(itemSelected==R.id.action_sort_by_rating){
            //movieAdapter.setMoviesData(null);
            loadMoviesData("top_rated");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}