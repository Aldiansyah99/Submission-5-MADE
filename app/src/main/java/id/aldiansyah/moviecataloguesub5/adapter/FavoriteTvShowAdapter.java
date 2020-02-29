package id.aldiansyah.moviecataloguesub5.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;

import java.util.List;

import id.aldiansyah.moviecataloguesub5.BuildConfig;
import id.aldiansyah.moviecataloguesub5.R;
import id.aldiansyah.moviecataloguesub5.detail.TvShowDetailActivity;
import id.aldiansyah.moviecataloguesub5.helper.database.TvShowDbHelper;
import id.aldiansyah.moviecataloguesub5.models.TvShowData;
import id.aldiansyah.moviecataloguesub5.models.TvShowLocalData;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.FavTvShowViewHolder> {
    private List<TvShowLocalData> favTvShow;
    private Context context;
    private TvShowDbHelper db;

    public FavoriteTvShowAdapter(Context context, List<TvShowLocalData> favTvShow) {
        this.favTvShow = favTvShow;
        this.context = context;
    }

    @NonNull
    @Override
    public FavTvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_show, parent, false);
        return new FavTvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavTvShowViewHolder holder, int position) {
        holder.bind(favTvShow.get(position));
    }

    @Override
    public int getItemCount() {
        return favTvShow.size();
    }

    class FavTvShowViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvVoteAverage;
        ImageView imgPoster;

        FavTvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_tvShow);
            tvVoteAverage = itemView.findViewById(R.id.tv_vote_average_tvShow);
            imgPoster = itemView.findViewById(R.id.img_poster_tvShow);
        }

        void bind(TvShowLocalData tvShow) {
            String vote_average = Double.toString(tvShow.getVote_average());

            tvName.setText(tvShow.getName());
            tvVoteAverage.setText(vote_average);
            Glide.with(context)
                    .load(BuildConfig.URL_IMAGE_POSTER + tvShow.getPoster())
                    .centerCrop()
                    .placeholder(R.drawable.ic_image)
                    .into(imgPoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final int position = getAdapterPosition();
                    final PopupMenu menu = new PopupMenu(context, v);
                    menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.action_detail:
                                    showDetailTvShow(position);
                                    break;

                                case R.id.action_delete:
                                    deleteTvShow(position, v);
                                    break;
                            }
                            return true;
                        }
                    });
                    menu.show();
                }
            });
        }
    }

    private void showDetailTvShow(int position) {
        Intent intent = new Intent(context, TvShowDetailActivity.class);
        try {
            TvShowData resultTvShow = new TvShowData();
            resultTvShow.setId(favTvShow.get(position).getId());
            resultTvShow.setName(favTvShow.get(position).getName());
            resultTvShow.setVote_average(favTvShow.get(position).getVote_average());
            resultTvShow.setFirst_air_date(favTvShow.get(position).getFirst_air_date());
            resultTvShow.setPopularity(favTvShow.get(position).getPopularity());
            resultTvShow.setOverview(favTvShow.get(position).getOverview());
            resultTvShow.setPoster(favTvShow.get(position).getPoster());
            resultTvShow.setBackdrop(favTvShow.get(position).getBackdrop());
            intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, resultTvShow);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteTvShow(final int position, View v) {
        db = Room.databaseBuilder(context, TvShowDbHelper.class, "tvshowdb").allowMainThreadQueries().build();

        final TvShowLocalData tvShow = favTvShow.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Delete This Favorite Tv Show?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                db.tvShowDAO().deleteTvShow(tvShow);
                favTvShow.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, favTvShow.size());
                Toast.makeText(context, R.string.text_delete_succsessfully, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
