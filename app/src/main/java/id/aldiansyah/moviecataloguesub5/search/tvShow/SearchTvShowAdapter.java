package id.aldiansyah.moviecataloguesub5.search.tvShow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.aldiansyah.moviecataloguesub5.BuildConfig;
import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.detail.MoviesDetailActivity;
import id.aldiansyah.moviecataloguesub5.detail.TvShowDetailActivity;
import id.aldiansyah.moviecataloguesub5.models.MoviesData;
import id.aldiansyah.moviecataloguesub5.models.TvShowData;

public class SearchTvShowAdapter extends RecyclerView.Adapter<SearchTvShowAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TvShowData> mDataTvShow = new ArrayList<>();

    public SearchTvShowAdapter(Context context) {
        this.context = context;
    }

    public void setDataTvShow(ArrayList<TvShowData> items) {
        mDataTvShow.clear();
        mDataTvShow.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchTvShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv_show, viewGroup, false);
        return new SearchTvShowAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTvShowAdapter.ViewHolder viewHolder, int i) {
        if (mDataTvShow.size() != 0) {
            final TvShowData data = mDataTvShow.get(i);
            viewHolder.bind(data);
        }
    }

    @Override
    public int getItemCount() {
        int data = 0;
        if (mDataTvShow.size() != 0) {
            data = mDataTvShow.size();
        }
        return data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvName, tvVoteAverage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster_tvShow);
            tvName = itemView.findViewById(R.id.tv_name_tvShow);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_average_tvShow);
        }

        void bind(TvShowData data) {
            String vote_average = Double.toString(data.getVote_average());
            Glide.with(context)
                    .load(BuildConfig.URL_IMAGE_POSTER + data.getPoster())
                    .apply(new RequestOptions().centerCrop())
                    .into(imgPoster);
            tvName.setText(data.getName());
            tvVoteAverage.setText(vote_average);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, TvShowDetailActivity.class);
                    try {
                        TvShowData tvShowData = new TvShowData();
                        tvShowData.setId(mDataTvShow.get(position).getId());
                        tvShowData.setName(mDataTvShow.get(position).getName());
                        tvShowData.setVote_average(mDataTvShow.get(position).getVote_average());
                        tvShowData.setFirst_air_date(mDataTvShow.get(position).getFirst_air_date());
                        tvShowData.setPopularity(mDataTvShow.get(position).getPopularity());
                        tvShowData.setOverview(mDataTvShow.get(position).getOverview());
                        tvShowData.setPoster(mDataTvShow.get(position).getPoster());
                        tvShowData.setBackdrop(mDataTvShow.get(position).getBackdrop());
                        intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, tvShowData);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
