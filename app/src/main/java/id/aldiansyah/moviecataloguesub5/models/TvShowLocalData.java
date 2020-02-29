package id.aldiansyah.moviecataloguesub5.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TvShowLocalData implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "data_id")
    public int dataId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "vote_average")
    private Double vote_average;
    @ColumnInfo(name = "poster_path")
    private String poster;
    @ColumnInfo(name = "backdrop_path")
    private String backdrop;
    @ColumnInfo(name = "first_air_date")
    private String first_air_date;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "popularity")
    private String popularity;
    @ColumnInfo(name = "vote_count")
    private int vote_count;

    public TvShowLocalData() {
    }

    protected TvShowLocalData(Parcel in) {
        id = in.readInt();
        dataId = in.readInt();
        name = in.readString();
        if (in.readByte() == 0) {
            vote_average = null;
        } else {
            vote_average = in.readDouble();
        }
        poster = in.readString();
        backdrop = in.readString();
        first_air_date = in.readString();
        overview = in.readString();
        popularity = in.readString();
        vote_count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(dataId);
        dest.writeString(name);
        if (vote_average == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(vote_average);
        }
        dest.writeString(poster);
        dest.writeString(backdrop);
        dest.writeString(first_air_date);
        dest.writeString(overview);
        dest.writeString(popularity);
        dest.writeInt(vote_count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TvShowLocalData> CREATOR = new Creator<TvShowLocalData>() {
        @Override
        public TvShowLocalData createFromParcel(Parcel in) {
            return new TvShowLocalData(in);
        }

        @Override
        public TvShowLocalData[] newArray(int size) {
            return new TvShowLocalData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }
}
