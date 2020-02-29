package id.aldiansyah.moviecataloguesub5.detail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.Objects;

import id.aldiansyah.moviecataloguesub5.BuildConfig;
import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.helper.database.MoviesDbHelper;
import id.aldiansyah.moviecataloguesub5.models.MoviesData;
import id.aldiansyah.moviecataloguesub5.models.MoviesLocalData;

public class MoviesDetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    private TextView tvTitle, tvVoteAverage, tvReleaseDate, tvOverview, tvPopularity;
    private ImageView imgPoster, imgBackdrop;
    private ProgressBar progressBar;
    private ShineButton btn_fav;
    private MoviesLocalData resultMovies;
    private MoviesData moviesData;
    private MoviesDbHelper db;
    private int dataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_detail_movies);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Movies Detail");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();

        moviesData = getIntent().getParcelableExtra(EXTRA_MOVIE);
        dataId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
        if (dataId == 0) dataId = moviesData.getId();

        progressBar.setVisibility(View.VISIBLE);

        showDetail();

        db = Room.databaseBuilder(getApplicationContext(), MoviesDbHelper.class, "moviesdb").allowMainThreadQueries().build();
        resultMovies = db.moviesDAO().selectDetailMovies(dataId);
        if (resultMovies != null) {
            btn_fav.setChecked(true);
        } else {
            btn_fav.setChecked(false);
        }

        btn_fav.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (checked) {
                    resultMovies = new MoviesLocalData();
                    resultMovies.setDataId(moviesData.getId());
                    resultMovies.setTitle(moviesData.getTitle());
                    resultMovies.setVoteAverage(moviesData.getVote_average());
                    resultMovies.setPosterPath(moviesData.getPoster());
                    resultMovies.setOverview(moviesData.getOverview());
                    resultMovies.setPopularity(moviesData.getPopularity());
                    resultMovies.setBackdrop(moviesData.getBackdrop());
                    resultMovies.setFavorite(BuildConfig.URL_IMAGE_POSTER_185 + moviesData.getPoster());
                    db.moviesDAO().insertMovies(resultMovies);
                    Toast.makeText(MoviesDetailActivity.this, R.string.text_add_success, Toast.LENGTH_SHORT).show();
                } else {
                    db.moviesDAO().deleteMovies(resultMovies);
                    btn_fav.setEnabled(false);
                    Toast.makeText(getApplicationContext(), R.string.text_delete_succsessfully, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void init() {
        tvTitle = findViewById(R.id.tv_title_movies_detail);
        tvVoteAverage = findViewById(R.id.tv_vote_average_movies_detail);
        tvReleaseDate = findViewById(R.id.tv_release_movies_detail);
        tvPopularity = findViewById(R.id.tv_popularity_movies_detail);
        tvOverview = findViewById(R.id.tv_overview_movies_detail);
        imgPoster = findViewById(R.id.img_poster_movies_detail);
        imgBackdrop = findViewById(R.id.img_backdrop_movies_detail);
        progressBar = findViewById(R.id.progressBar_movies_detail);
        btn_fav = findViewById(R.id.btn_fav);
    }

    private void showDetail() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String vote_average = Double.toString(moviesData.getVote_average());
                        tvTitle.setText(moviesData.getTitle());
                        tvVoteAverage.setText(vote_average);
                        tvReleaseDate.setText(moviesData.getRelease_date());
                        tvOverview.setText(moviesData.getOverview());
                        tvPopularity.setText(moviesData.getPopularity());
                        Glide.with(getApplicationContext())
                                .load(BuildConfig.URL_IMAGE_POSTER + moviesData.getPoster())
                                .centerCrop()
                                .placeholder(R.drawable.ic_image)
                                .into(imgPoster);
                        Glide.with(getApplicationContext())
                                .load(BuildConfig.URL_IMAGE_BACKDROP + moviesData.getBackdrop())
                                .centerCrop()
                                .placeholder(R.drawable.ic_image)
                                .into(imgBackdrop);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }
}
