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
import id.aldiansyah.moviecataloguesub5.helper.database.TvShowDbHelper;
import id.aldiansyah.moviecataloguesub5.models.TvShowData;
import id.aldiansyah.moviecataloguesub5.models.TvShowLocalData;

public class TvShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    public static final String EXTRA_TV_SHOW_ID = "extra_tv_show_id";
    private TextView tvName, tvVoteAverage, tvFirstAirDate, tvOverview, tvVoteCount, tvPopularity;
    private ImageView imgPoster, imgBackdrop;
    private ProgressBar progressBar;
    private ShineButton btn_fav;
    private TvShowData tvShowData;
    private TvShowLocalData resultTvShow;
    private TvShowDbHelper db;
    private int dataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_tvShow_detail);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tv Show Detail");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();

        tvShowData = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        dataId = getIntent().getIntExtra(EXTRA_TV_SHOW_ID, 0);
        if (dataId == 0) dataId = tvShowData.getId();
        progressBar.setVisibility(View.VISIBLE);

        showDetail();

        db = Room.databaseBuilder(this, TvShowDbHelper.class, "tvshowdb").allowMainThreadQueries().build();
        resultTvShow = db.tvShowDAO().selectDetailTvShow(dataId);
        if (resultTvShow != null) {
            btn_fav.setChecked(true);
        } else {
            btn_fav.setChecked(false);
        }

        btn_fav.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if (checked) {
                    resultTvShow = new TvShowLocalData();
                    resultTvShow.setDataId(tvShowData.getId());
                    resultTvShow.setName(tvShowData.getName());
                    resultTvShow.setVote_average(tvShowData.getVote_average());
                    resultTvShow.setPoster(tvShowData.getPoster());
                    resultTvShow.setOverview(tvShowData.getOverview());
                    resultTvShow.setPopularity(tvShowData.getPopularity());
                    resultTvShow.setBackdrop(tvShowData.getBackdrop());
                    resultTvShow.setFirst_air_date(tvShowData.getFirst_air_date());
                    resultTvShow.setVote_count(tvShowData.getVote_count());
                    db.tvShowDAO().insertTvShow(resultTvShow);
                    Toast.makeText(TvShowDetailActivity.this, R.string.text_add_success, Toast.LENGTH_SHORT).show();
                } else {
                    db.tvShowDAO().deleteTvShow(resultTvShow);
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
        tvName = findViewById(R.id.tv_name_tvShow_detail);
        tvVoteAverage = findViewById(R.id.tv_vote_average_tvShow_detail);
        tvVoteCount = findViewById(R.id.tv_vote_count_tvShow_detail);
        tvFirstAirDate = findViewById(R.id.tv_first_air_date_tvShow_detail);
        tvPopularity = findViewById(R.id.tv_popularity_tvShow_detail);
        tvOverview = findViewById(R.id.tv_overview_tvShow_detail);
        imgPoster = findViewById(R.id.img_poster_tvShow_detail);
        imgBackdrop = findViewById(R.id.img_backdrop_tvShow_detail);
        progressBar = findViewById(R.id.progressBar_tvShow_detail);
        btn_fav = findViewById(R.id.btn_fav_tvShow);
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
                        String vote_average = Double.toString(tvShowData.getVote_average());
                        String vote_count = Integer.toString(tvShowData.getVote_count());

                        tvName.setText(tvShowData.getName());
                        tvVoteAverage.setText(vote_average);
                        tvVoteCount.setText(vote_count);
                        tvFirstAirDate.setText(tvShowData.getFirst_air_date());
                        tvOverview.setText(tvShowData.getOverview());
                        tvPopularity.setText(tvShowData.getPopularity());
                        Glide.with(getApplicationContext())
                                .load(BuildConfig.URL_IMAGE_POSTER + tvShowData.getPoster())
                                .centerCrop()
                                .placeholder(R.drawable.ic_image)
                                .into(imgPoster);
                        Glide.with(getApplicationContext())
                                .load(BuildConfig.URL_IMAGE_BACKDROP + tvShowData.getBackdrop())
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
