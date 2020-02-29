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
import id.aldiansyah.moviecataloguesub5.models.TvShowData;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.tvShowViewHolder> {
    private Context context;
    private ArrayList<TvShowData> mData = new ArrayList<>();

    public TvShowAdapter(Context context, ArrayList<TvShowData> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public tvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_show, parent, false);
        return new tvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tvShowViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class tvShowViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvVoteAverage;
        ImageView imgPoster;

        tvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_tvShow);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_average_tvShow);
            imgPoster = itemView.findViewById(R.id.img_poster_tvShow);
        }

        void bind(TvShowData tvShow) {
            String vote_average = Double.toString(tvShow.getVote_average());

            tvName.setText(tvShow.getName());
            tvVoteAverage.setText(vote_average);
            Glide.with(context)
                    .load(BuildConfig.URL_IMAGE_POSTER + tvShow.getPoster())
                    .centerCrop()
                    .placeholder(R.drawable.ic_image)
                    .into(imgPoster);
        }
    }
}
