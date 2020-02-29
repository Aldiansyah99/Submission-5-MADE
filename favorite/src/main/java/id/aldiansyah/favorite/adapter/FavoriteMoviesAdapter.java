package id.aldiansyah.favorite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.aldiansyah.favorite.BuildConfig;
import id.aldiansyah.favorite.R;
import id.aldiansyah.favorite.data.MoviesData;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.FavMoviesViewHolder> {
    private ArrayList<MoviesData> favMovies = new ArrayList<>();
    private Context context;

    public FavoriteMoviesAdapter(Context context) {
        this.context = context;
    }

    public void setListMovies(ArrayList<MoviesData> listMovies) {
        this.favMovies.clear();
        this.favMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new FavMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMoviesViewHolder holder, final int position) {
        holder.bind(favMovies.get(position));

    }

    @Override
    public int getItemCount() {
        return favMovies.size();
    }

    class FavMoviesViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvVoteAverage;
        ImageView imgPoster;

        FavMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title_movies);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_average_movies);
            imgPoster = itemView.findViewById(R.id.img_poster_movies);
        }

        void bind(MoviesData movies) {
            String vote_average = Double.toString(movies.getVote_average());

            tvTitle.setText(movies.getTitle());
            tvVoteAverage.setText(vote_average);
            Glide.with(context)
                    .load(BuildConfig.URL_IMAGE_POSTER + movies.getPoster())
                    .centerCrop()
                    .placeholder(R.drawable.ic_image)
                    .into(imgPoster);
        }
    }
}
