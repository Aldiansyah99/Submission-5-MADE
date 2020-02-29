package id.aldiansyah.moviecataloguesub5.search.movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.aldiansyah.moviecataloguesub5.ItemClickSupport;
import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.detail.MoviesDetailActivity;
import id.aldiansyah.moviecataloguesub5.models.MoviesData;
import id.aldiansyah.moviecataloguesub5.models.MoviesLocalData;

public class SearchActivity extends AppCompatActivity {

    private SearchAdapter movieAdapter;
    private SearchViewModel movieViewModel;
    private RecyclerView rvMovie;
    public static String EXTRA_TYPE = "EXTRA_TYPE";
    public static String EXTRA_QUERY = "EXTRA_QUERY";
    String type, query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search Movies");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvMovie = findViewById(R.id.rv_movies);

        movieViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        type = getIntent().getStringExtra(EXTRA_TYPE);
        query = getIntent().getStringExtra(EXTRA_QUERY);

        if (type.equalsIgnoreCase("moviesdb")) {
            movieViewModel.getData(type, query);
            movieViewModel.getMoviesData().observe(this, getMovies);
        }

        movieAdapter = new SearchAdapter(this);
        movieAdapter.notifyDataSetChanged();

        showRecycleViewMovie();
    }

    private Observer<ArrayList<MoviesData>> getMovies = new Observer<ArrayList<MoviesData>>() {
        @Override
        public void onChanged(ArrayList<MoviesData> moviesData) {
            if (moviesData != null) {
                movieAdapter.setDataMovie(moviesData);
            }
        }
    };

    private void showRecycleViewMovie() {
        rvMovie.setLayoutManager(new GridLayoutManager(this, 3));
        movieAdapter = new SearchAdapter(this);
        rvMovie.setAdapter(movieAdapter);
    }
}
