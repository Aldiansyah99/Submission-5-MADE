package id.aldiansyah.moviecataloguesub5.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.aldiansyah.moviecataloguesub5.ItemClickSupport;
import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.adapter.MoviesAdapter;
import id.aldiansyah.moviecataloguesub5.detail.MoviesDetailActivity;
import id.aldiansyah.moviecataloguesub5.models.ModelMovies;
import id.aldiansyah.moviecataloguesub5.models.MoviesData;
import id.aldiansyah.moviecataloguesub5.viewmodel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    private MoviesAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArrayList<MoviesData> listMovies = new ArrayList<>();

    public MoviesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_movie);
        progressBar = view.findViewById(R.id.progress_bar_movies);
        progressBar.showContextMenu();

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.initializeMovies();
        viewModel.getMoviesModel().observe(getViewLifecycleOwner(), new Observer<ModelMovies>() {
            @Override
            public void onChanged(ModelMovies modelMovies) {
                showLoading(false);
                ArrayList<MoviesData> resultMovies = modelMovies.getResultMovies();
                listMovies.addAll(resultMovies);
                adapter.notifyDataSetChanged();
            }
        });

        showRecyclerMovies();
        clickMovies();

    }

    private void showRecyclerMovies() {
        if (adapter == null) {
            adapter = new MoviesAdapter(getActivity(), listMovies);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void clickMovies() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnitemClickListener() {
            @Override
            public void onitemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), MoviesDetailActivity.class);
                try {
                    MoviesData resultMovies = new MoviesData();
                    resultMovies.setId(listMovies.get(position).getId());
                    resultMovies.setTitle(listMovies.get(position).getTitle());
                    resultMovies.setVote_average(listMovies.get(position).getVote_average());
                    resultMovies.setRelease_date(listMovies.get(position).getRelease_date());
                    resultMovies.setPopularity(listMovies.get(position).getPopularity());
                    resultMovies.setOverview(listMovies.get(position).getOverview());
                    resultMovies.setPoster(listMovies.get(position).getPoster());
                    resultMovies.setBackdrop(listMovies.get(position).getBackdrop());
                    intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, resultMovies);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
