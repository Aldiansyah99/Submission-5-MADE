package id.aldiansyah.moviecataloguesub5.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;
import java.util.Objects;

import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.adapter.FavoriteTvShowAdapter;
import id.aldiansyah.moviecataloguesub5.helper.database.TvShowDbHelper;
import id.aldiansyah.moviecataloguesub5.models.TvShowLocalData;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView tvNoItems;
    private FavoriteTvShowAdapter adapter;
    private TvShowDbHelper db;

    public FavoriteTvShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_fav_tvShow);
        tvNoItems = view.findViewById(R.id.tv_no_items_fav_tvShow);

        db = Room.databaseBuilder(Objects.requireNonNull(getActivity()), TvShowDbHelper.class, "tvshowdb").build();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setHasFixedSize(true);

        new AsyncTask<Void, Void, List>() {
            @Override
            protected List doInBackground(Void... voids) {
                List<TvShowLocalData> cek = db.tvShowDAO().showAllTvShow();
                return cek;
            }

            @Override
            protected void onPostExecute(List list) {
                super.onPostExecute(list);
                if (list.isEmpty()) {
                    tvNoItems.setVisibility(View.VISIBLE);
                } else {
                    tvNoItems.setVisibility(View.GONE);
                    adapter = new FavoriteTvShowAdapter(getActivity(), list);
                    recyclerView.setAdapter(adapter);
                }
            }
        }.execute();
    }
}
