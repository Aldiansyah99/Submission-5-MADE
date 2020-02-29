package id.aldiansyah.moviecataloguesub5.search.tvShow;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.models.TvShowData;

public class SearchTvShowActivity extends AppCompatActivity {

    private SearchTvShowAdapter tvShowAdapter;
    private SearchTvShowViewModel searchTvShowViewModel;
    private RecyclerView rvTvShow;
    public static String EXTRA_TYPE = "EXTRA_TYPE";
    public static String EXTRA_QUERY = "EXTRA_QUERY";
    String type, query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv_show);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search Tv Show");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvTvShow = findViewById(R.id.rv_tvShow);

        searchTvShowViewModel = new ViewModelProvider(this).get(SearchTvShowViewModel.class);

        type = getIntent().getStringExtra(EXTRA_TYPE);
        query = getIntent().getStringExtra(EXTRA_QUERY);

        if (type.equalsIgnoreCase("tvshowdb")) {
            searchTvShowViewModel.getData(type, query);
            searchTvShowViewModel.getTvShowData().observe(this, getTvShow);
        }

        tvShowAdapter = new SearchTvShowAdapter(this);
        tvShowAdapter.notifyDataSetChanged();

        showRecycleViewTvShow();
    }

    private Observer<ArrayList<TvShowData>> getTvShow = new Observer<ArrayList<TvShowData>>() {
        @Override
        public void onChanged(ArrayList<TvShowData> tvShowData) {
            if (tvShowData != null) {
                tvShowAdapter.setDataTvShow(tvShowData);
            }
        }
    };

    private void showRecycleViewTvShow() {
        rvTvShow.setLayoutManager(new GridLayoutManager(this, 3));
        tvShowAdapter = new SearchTvShowAdapter(this);
        rvTvShow.setAdapter(tvShowAdapter);
    }
}
