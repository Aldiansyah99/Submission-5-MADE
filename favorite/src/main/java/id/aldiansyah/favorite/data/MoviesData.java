package id.aldiansyah.favorite.data;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class MoviesData {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private Double vote_average;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String poster;

    public MoviesData(int id, String title, Double vote_average, String poster) {
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
