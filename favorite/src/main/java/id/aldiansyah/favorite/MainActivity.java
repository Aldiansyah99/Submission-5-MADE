package id.aldiansyah.favorite;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import id.aldiansyah.favorite.adapter.FavoriteMoviesAdapter;
import id.aldiansyah.favorite.data.LoadFavoriteDataCallback;
import id.aldiansyah.favorite.data.MoviesData;

import static id.aldiansyah.favorite.helper.DatabaseContract.MoviesColumns.CONTENT_URI;
import static id.aldiansyah.favorite.helper.MappingHelper.mapCursorToArrayListMovies;

public class MainActivity extends AppCompatActivity implements LoadFavoriteDataCallback {

    private RecyclerView recyclerView;
    private DataObserver dataObserver;
    private static FavoriteMoviesAdapter moviesAdapter;
    private TextView tvNoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_fav_movies);
        tvNoItems = findViewById(R.id.tv_no_items_fav_movies);
        moviesAdapter = new FavoriteMoviesAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(moviesAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        dataObserver = new DataObserver(handler, this);
        Objects.requireNonNull(this).getContentResolver().registerContentObserver(CONTENT_URI, true, dataObserver);
        new getData(this, this).execute();
    }

    @Override
    public void postExecute(Cursor favorite) {
        ArrayList<MoviesData> listMovies = mapCursorToArrayListMovies(favorite);
        if (listMovies.isEmpty()) {
            tvNoItems.setVisibility(View.VISIBLE);
        } else {
            tvNoItems.setVisibility(View.GONE);
            moviesAdapter.setListMovies(listMovies);
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoriteDataCallback> weakCallback;

        private getData(Context context, LoadFavoriteDataCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }
    }

    static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }
}
