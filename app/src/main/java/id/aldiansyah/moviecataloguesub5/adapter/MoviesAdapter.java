package id.aldiansyah.moviecataloguesub5.adapter;

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

import id.aldiansyah.moviecataloguesub5.BuildConfig;
import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.models.MoviesData;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private Context context;
    private ArrayList<MoviesData> mData = new ArrayList<>();

    public MoviesAdapter(Context context, ArrayList<MoviesData> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvVoteAverage;
        ImageView imgPoster;

        MoviesViewHolder(@NonNull View itemView) {
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
