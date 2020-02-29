package id.aldiansyah.moviecataloguesub5.search.movies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import id.aldiansyah.moviecataloguesub5.BuildConfig;
import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.detail.MoviesDetailActivity;
import id.aldiansyah.moviecataloguesub5.models.MoviesData;
import id.aldiansyah.moviecataloguesub5.models.MoviesLocalData;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MoviesData> mDataMovie = new ArrayList<>();

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setDataMovie(ArrayList<MoviesData> items) {
        mDataMovie.clear();
        mDataMovie.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new SearchAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder viewHolder, int i) {
        if (mDataMovie.size() != 0) {
            final MoviesData data = mDataMovie.get(i);
            viewHolder.bind(data);
        }
    }

    @Override
    public int getItemCount() {
        int data = 0;
        if (mDataMovie.size() != 0) {
            data = mDataMovie.size();
        }
        return data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvVoteAverage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_movies);
            tvTitle = itemView.findViewById(R.id.tv_title_movies);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_average_movies);
        }

        void bind(MoviesData data) {
            String vote_average = Double.toString(data.getVote_average());
            Glide.with(context)
                    .load(BuildConfig.URL_IMAGE_POSTER + data.getPoster())
                    .apply(new RequestOptions().centerCrop())
                    .into(imgPoster);
            tvTitle.setText(data.getTitle());
            tvVoteAverage.setText(vote_average);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, MoviesDetailActivity.class);
                    try {
                        MoviesData resultMovies = new MoviesData();
                        resultMovies.setId(mDataMovie.get(position).getId());
                        resultMovies.setTitle(mDataMovie.get(position).getTitle());
                        resultMovies.setVote_average(mDataMovie.get(position).getVote_average());
                        resultMovies.setRelease_date(mDataMovie.get(position).getRelease_date());
                        resultMovies.setPopularity(mDataMovie.get(position).getPopularity());
                        resultMovies.setOverview(mDataMovie.get(position).getOverview());
                        resultMovies.setPoster(mDataMovie.get(position).getPoster());
                        resultMovies.setBackdrop(mDataMovie.get(position).getBackdrop());
                        intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, resultMovies);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
